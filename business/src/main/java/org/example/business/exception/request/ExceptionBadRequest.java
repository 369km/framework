package org.example.business.exception.request;

import org.example.business.exception.BaseException;
import org.example.business.exception.ExceptionEnumInterface;
import org.example.business.exception.ExceptionStatus;

public class ExceptionBadRequest extends BaseException {
    protected ExceptionBadRequest(ExceptionStatus status) {
        super(status);
    }

    protected ExceptionBadRequest(ExceptionStatus status, Object data) {
        super(status, data);
    }

    protected ExceptionBadRequest(ExceptionEnumInterface enumCode) {
        super(enumCode);
    }

    protected ExceptionBadRequest(ExceptionEnumInterface enumCode, Object data) {
        super(enumCode, data);
    }
}
