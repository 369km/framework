package org.example.business.user;

import org.apache.commons.lang3.StringUtils;
import org.example.business.captcha.CaptchaRequest;
import org.example.business.captcha.CaptchaResponse;
import org.example.business.captcha.CaptchaService;
import org.example.business.captcha.CaptchaStatus;
import org.example.common.Constant;
import org.example.common.cache.CacheService;
import org.example.common.exception.request.ExceptionBadRequest;
import org.example.common.exception.request.ExceptionEnum400;
import org.example.common.rest.user.UserLogin;
import org.example.common.token.Token;
import org.example.common.token.TokenService;
import org.example.common.token.TokenThreadLocal;
import org.example.common.utils.SecurityUtils;
import org.example.data.model.user.User;
import org.example.data.service.user.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private SecurityUtils securityUtils;

    public User save(User user) {
        return userDataService.save(user);
    }

    @Override
    public Page<User> findAll(User user, Pageable pageable) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        return userDataService.findAll(Example.of(user, exampleMatcher), pageable);
    }

    @Override
    public List<User> findAll() {
        return userDataService.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDataService.findById(id).orElseGet(User::new);
    }

    @Override
    public CaptchaResponse captcha() {
        return captchaService.generateCaptcha().orElse(new CaptchaResponse());
    }

    @Override
    public User login(UserLogin request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //1. 校验图形验证码
        validateCaptcha(request.getCaptchaNum(), request.getCaptchaKey());
        User user;
        //2. 校验账号密码
        try {
            user = validateAccountAndPassword(request.getLoginAccount(), request.getLoginPassword());
        } catch (ExceptionBadRequest e) {
            //3.登录错误次数过多，锁定账号24小时
            banAccount(request.getLoginAccount());
            throw e;
        }
        //4. 存储token
        storeToken(user.getId(), httpServletRequest, httpServletResponse);
        //5. 返回user
        return user;
    }

    private void validateCaptcha(String captchaNum, String captchaKey) {
        if (StringUtils.isEmpty(captchaNum) || StringUtils.isEmpty(captchaKey)) {
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_CAPTCHA);
        }
        CaptchaRequest request = new CaptchaRequest();
        request.setCaptcha(captchaNum);
        request.setKey(captchaKey);
        CaptchaStatus status = captchaService.validateCaptcha(request);
        if (CaptchaStatus.EXPIRED == status) {
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_CAPTCHA_EXPIRED);
        }
        if (CaptchaStatus.FAIL == status) {
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_CAPTCHA);
        }
    }

    private User validateAccountAndPassword(String loginAccount, String loginPassword) {
        return userDataService.findByLoginAccountAndLoginPassword(loginAccount, securityUtils.encrypt(loginPassword)).orElseThrow(() -> new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_LOGIN));
    }

    private void storeToken(Long userId, HttpServletRequest request, HttpServletResponse response) {
        Token token = new Token(userId);
        TokenThreadLocal.setToken(token);
        tokenService.storeToken(response, token, request.getHeader(Constant.TOKEN_NAME));
    }

    private void banAccount(String loginAccount) {
        //错误5次锁定账号24小时
        canBan(loginAccount);
        Long errorCount = cacheService.incrBy(loginErrorCountRedisKey(loginAccount), 1L);
        if (errorCount == 1L) {
            cacheService.set(loginErrorCountRedisKey(loginAccount), String.valueOf(errorCount));
            return;
        }
        if (errorCount == 2L) {
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_LOGIN_ERROR_COUNT_TWO);

        }
        if (errorCount == 5L) {
            cacheService.incrBy(loginErrorCountRedisKey(loginAccount), 1L);
            cacheService.expire(loginErrorCountRedisKey(loginAccount), 24, TimeUnit.HOURS);
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_LOGIN_ERROR_COUNT_FIVE);
        }

    }

    private void canBan(String loginAccount) {
        Long errorCount = Optional.ofNullable(cacheService.get(loginErrorCountRedisKey(loginAccount)))
                .map(Objects::toString)
                .map(Long::valueOf)
                .orElse(0L);
        if (errorCount >= 6) {
            throw new ExceptionBadRequest(ExceptionEnum400.BAD_REQUEST_LOGIN_ERROR_COUNT_SIX);
        }
    }

    private String loginErrorCountRedisKey(String loginAccount) {
        return Constant.LOGIN_ERROR_COUNT_PREX + loginAccount;

    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().matches("((\\w)+-)*" + Constant.TOKEN_NAME)) {
                    LOGGER.info("cookie name: {}", cookie.getName());
                    Cookie newCookie = tokenService.createCookie("", httpServletRequest.getHeader(Constant.TOKEN_NAME));
                    httpServletResponse.addCookie(newCookie);
                    tokenService.removeTokenFromRedis(httpServletRequest);
                }
            }
        }
    }
}
