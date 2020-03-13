package org.example.common.exception;

public enum ExceptionStatus {
    SUCCESS("000000", "请求成功"),
    ;

    private String code;
    private String message;

    ExceptionStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
