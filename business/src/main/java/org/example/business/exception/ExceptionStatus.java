package org.example.business.exception;

public enum ExceptionStatus {
    SUCCESS("000000", "请求成功"),
    PARAM_NOT_CORRECT("001001", "请求参数不正确"),
    SERVICE_INTERNAL("007002", "内部错误"),
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
