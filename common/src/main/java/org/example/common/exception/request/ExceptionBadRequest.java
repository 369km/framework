package org.example.common.exception.request;

import org.example.common.exception.BaseException;
import org.example.common.exception.ExceptionEnumInterface;
import org.example.common.exception.ExceptionStatus;

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
