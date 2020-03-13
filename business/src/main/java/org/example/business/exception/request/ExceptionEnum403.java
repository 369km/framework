package org.example.business.exception.request;

import org.example.common.Constant;

public enum ExceptionEnum403 implements ExceptionEnumInterface403 {
    BAD_REQUEST_LOGIN_ERROR(100, "登录参数错误"),
    ;

    private int code;
    private String message;

    ExceptionEnum403(int code, String message) {
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
