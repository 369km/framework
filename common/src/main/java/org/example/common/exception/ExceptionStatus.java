package org.example.common.exception;

public enum ExceptionStatus {
    SUCCESS("000000", "请求成功"),
    PARAM_NOT_CORRECT("000001", "参数错误"),
    SERVICE_INTERNAL("000002", "服务器内部错误"),
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
