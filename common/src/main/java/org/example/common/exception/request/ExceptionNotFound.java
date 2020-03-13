package org.example.common.exception.request;


import org.example.common.exception.BaseException;
import org.example.common.exception.ExceptionEnumInterface;
import org.example.common.exception.ExceptionStatus;

public class ExceptionNotFound extends BaseException {
    protected ExceptionNotFound(ExceptionStatus status) {
        super(status);
    }

    protected ExceptionNotFound(ExceptionStatus status, Object data) {
        super(status, data);
    }

    protected ExceptionNotFound(ExceptionEnumInterface enumCode) {
        super(enumCode);
    }

    protected ExceptionNotFound(ExceptionEnumInterface enumCode, Object data) {
        super(enumCode, data);
    }
}
