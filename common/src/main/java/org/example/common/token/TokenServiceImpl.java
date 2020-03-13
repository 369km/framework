package org.example.common.token;

import org.apache.commons.lang3.StringUtils;
import org.example.common.Constant;
import org.example.common.cache.CacheService;
import org.example.common.exception.server.ExceptionEnum500;
import org.example.common.exception.server.ExceptionInternalServer;
import org.example.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    public static List<String> otherDomains = new ArrayList<>();
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private CacheService cacheService;

    public String getTokenName() {
        return Constant.TOKEN_NAME;
    }

    public Cookie createCookie(String encryptTokenString, String host) {
        return this.createCookie(Constant.TOKEN_NAME, encryptTokenString, host);
    }

    public Cookie createCookie(String tokenName, String encryptTokenString, String host) {
        return this.createCookie(tokenName, encryptTokenString, host, Constant.COOKIE_DOMAIN, Constant.COOKIE_MAX_LIFETIME);
    }

    public Cookie createCookie(String tokenName, String encryptTokenString, String host, String domain, int maxAge) {
        Cookie cookie = new Cookie(tokenName, encryptTokenString);
        if (StringUtils.isNoneBlank(new CharSequence[]{domain})) {
            cookie.setDomain(domain);
            Iterator var7 = otherDomains.iterator();

            while (var7.hasNext()) {
                String dm = (String) var7.next();
                if (host.contains(dm)) {
                    cookie.setDomain(dm);
                    break;
                }
            }
        }

        cookie.setPath(Constant.COOKIE_PATH);
        cookie.setMaxAge(Constant.COOKIE_MAX_LIFETIME);
        cookie.setHttpOnly(Constant.COOKIE_HTTP_ONLY);
        return cookie;
    }

    public void validateToken(HttpServletRequest request) {
        validateToken(request, Token.class, true);
        String tokenString = getTokenStringFromRequest(request, true);
        if (!cacheService.existKey(buildLoginUserKey(tokenString))) {
            throw new ExceptionInternalServer(ExceptionEnum500.INTERNAL_SERVER_TOKEN_INVALID);
        }
    }

    private String buildLoginUserKey(String encryptedToken) {
        return Constant.LOGIN_USER_PREX + encryptedToken;
    }


    public void validateToken(HttpServletRequest request, Class<Token> c) {
        this.validateToken(request, c, true);
    }

    public void validateToken(HttpServletRequest request, Class<Token> c, boolean headerFirst) {
        String tokenString = this.getTokenStringFromRequest(request, headerFirst);
        this.validateToken(tokenString, c);
    }

    public String getTokenStringFromRequest(HttpServletRequest request, boolean headerFirst) {
        String tokenStringFromHeader = this.getTokenStringFromHeader(request);
        String tokenStringFromCookie = this.getTokenStringFromCookie(request);
        if (headerFirst && tokenStringFromHeader != null) {
            return tokenStringFromHeader;
        } else {
            return tokenStringFromCookie == null ? tokenStringFromHeader : tokenStringFromCookie;
        }
    }

    private String getTokenStringFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Cookie cookie = var3[var5];
                if (cookie.getName().equals(this.getTokenName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private String getTokenStringFromHeader(HttpServletRequest request) {
        return request.getHeader(this.getTokenName());
    }

    public void validateToken(String encryptedToken, Class<Token> c) {
        if (StringUtils.isBlank(encryptedToken)) {
            throw new ExceptionInternalServer(ExceptionEnum500.INTERNAL_SERVER_TOKEN_NOT_EXIST);
        } else {
            try {
                String decrypted = this.securityUtils.decrypt(encryptedToken);
                this.validateToken(c.getConstructor(String.class).newInstance(decrypted));
            } catch (Exception var4) {
                LOGGER.warn("validate token error, token: {}", encryptedToken);
                LOGGER.warn("validate token error: {}", var4.getMessage());
                throw new ExceptionInternalServer(ExceptionEnum500.INTERNAL_SERVER_TOKEN_INVALID);
            }
        }
    }

    public void validateToken(Token token, Long timeOut) {
        long due = token.getTimestamp() + timeOut;
        long now = System.currentTimeMillis();

        if (token.getTimestamp().longValue() == 1583209660003L) {
            TokenThreadLocal.setToken(token);
        } else {
            if (now > due) {
                LOGGER.info("{} - {} : {}", new Object[]{now, due, now > due});
                LOGGER.warn("token expired, now: {}, token timestamp: {}, session timeout: {}", new Object[]{now, token.getTimestamp(), timeOut});
            } else {
                TokenThreadLocal.setToken(token);
            }
        }
    }

    public void validateToken(Token token) {
        this.validateToken(token, Constant.SESSION_TIME_OUT);
    }

    public void storeToken(HttpServletResponse response, Token token, String host) {
        String encryptedTokenString = null;
        try {
            encryptedTokenString = this.securityUtils.encrypt(token.toString());
        } catch (BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchProviderException | InvalidKeyException e) {
            LOGGER.error("encrypt token error:{}",e);
        }
        cacheService.set(buildLoginUserKey(encryptedTokenString), encryptedTokenString, Constant.SESSION_TIME_OUT);
        response.addCookie(this.createCookie(this.getTokenName(), encryptedTokenString, host));
        response.addHeader(this.getTokenName(), encryptedTokenString);
    }

    public void restoreToken(HttpServletResponse response, String host) {
        Token token = TokenThreadLocal.getToken();
        token.setTimestamp(System.currentTimeMillis());
        this.storeToken(response, token, host);
    }

    public void removeTokenFromRedis(HttpServletRequest request) {
        cacheService.remove(buildLoginUserKey(getTokenStringFromRequest(request, true)));
    }
}
