package org.example.business.user;

import org.example.business.BaseService;
import org.example.common.captcha.CaptchaResponse;
import org.example.common.rest.user.UserLogin;
import org.example.data.model.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService extends BaseService<User> {
    CaptchaResponse captcha();

    User login(UserLogin request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
