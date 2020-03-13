package org.example.business.exception.request;

import org.example.business.exception.BaseException;
import org.example.business.exception.ExceptionEnumInterface;
import org.example.business.exception.ExceptionStatus;

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
