package org.example.business.exception.request;

import org.example.common.Constant;

public enum ExceptionEnum400 implements ExceptionEnumInterface400 {
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
