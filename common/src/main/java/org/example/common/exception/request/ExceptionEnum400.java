package org.example.common.exception.request;

import org.example.common.Constant;

public enum ExceptionEnum400 implements ExceptionEnumInterface400 {
    BAD_REQUEST_CAPTCHA(100, "图形验证码不正确"),
    BAD_REQUEST_CAPTCHA_EXPIRED(101, "图形验证码过期"),
    BAD_REQUEST_LOGIN(102, "账号或密码错误"),
    BAD_REQUEST_LOGIN_ERROR_COUNT_TWO(103, "登录错误2次,如果超过5次,账号将被锁定"),
    BAD_REQUEST_LOGIN_ERROR_COUNT_FIVE(104, "登录错误5次,账号被锁定,请24小时后再登录"),
    BAD_REQUEST_LOGIN_ERROR_COUNT_SIX(105, "账号已锁定"),
    ;

    private int code;
    private String message;

    ExceptionEnum400(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getAppType() {
        return Constant.APP_TYPE;
    }
}
