package org.example.common.exception;
public class BaseException extends RuntimeException {

    protected String errorCode;

    protected String message;

    protected Object data;

    protected BaseException(ExceptionStatus status) {
        this.errorCode = status.getCode();
        this.message = status.getMessage();
    }

    protected BaseException(ExceptionStatus status, Object data) {
        this(status);
        this.data = data;
    }

    protected BaseException(ExceptionEnumInterface enumCode) {
        this.errorCode = String.format("%02d%s%03d", enumCode.getAppType(), enumCode.getType().value(), enumCode.getCode());
        this.message = enumCode.getMessage();
    }

    protected BaseException(ExceptionEnumInterface enumCode, Object data) {
        this(enumCode);
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

