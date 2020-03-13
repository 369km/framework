package org.example.common.exception.request;


import org.example.common.exception.BaseException;
import org.example.common.exception.ExceptionEnumInterface;
import org.example.common.exception.ExceptionStatus;

public class ExceptionNotFound extends BaseException {
    public ExceptionNotFound(ExceptionStatus status) {
        super(status);
    }

    public ExceptionNotFound(ExceptionStatus status, Object data) {
        super(status, data);
    }

    public ExceptionNotFound(ExceptionEnumInterface enumCode) {
        super(enumCode);
    }

    public ExceptionNotFound(ExceptionEnumInterface enumCode, Object data) {
        super(enumCode, data);
    }
}
