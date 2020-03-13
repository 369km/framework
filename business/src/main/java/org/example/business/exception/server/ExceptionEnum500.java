package org.example.business.exception.server;

import org.example.common.Constant;

public enum ExceptionEnum500 implements ExceptionEnumInterface500 {
    INTERNAL_SERVER_TOKEN_INVALID(100,"token不可使用"),
    INTERNAL_SERVER_TOKEN_NOT_EXIST(101,"token不存在"),
    ;
    private int code;
    private String message;

    ExceptionEnum500(int code, String message) {
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
