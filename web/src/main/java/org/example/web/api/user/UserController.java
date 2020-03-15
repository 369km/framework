package org.example.web.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.captcha.CaptchaResponse;
import org.example.business.user.UserService;
import org.example.common.Constant;
import org.example.common.rest.user.UserLogin;
import org.example.data.model.user.User;
import org.example.web.annotation.UserSwagger;
import org.example.web.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("人员")
@UserSwagger
@RestController
@RequestMapping(Constant.API + "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("添加人员")
    @PostMapping
    public RestResponse<User> save(@RequestBody User request) {
        return new RestResponse<>(userService.save(request));
    }

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha")
    public RestResponse<CaptchaResponse> captcha() {
        return new RestResponse<>(userService.captcha());
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public RestResponse<User> login(@RequestBody UserLogin request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new RestResponse<>(userService.login(request, httpServletRequest, httpServletResponse));
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public RestResponse<Void> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        userService.logout(httpServletRequest, httpServletResponse);
        return new RestResponse<>();
    }

}
