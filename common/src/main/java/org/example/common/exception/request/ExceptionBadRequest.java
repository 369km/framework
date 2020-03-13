package org.example.common.exception.request;

import org.example.common.exception.BaseException;
import org.example.common.exception.ExceptionEnumInterface;
import org.example.common.exception.ExceptionStatus;

public class ExceptionBadRequest extends BaseException {
    public ExceptionBadRequest(ExceptionStatus status) {
        super(status);
    }

    public ExceptionBadRequest(ExceptionStatus status, Object data) {
        super(status, data);
    }

    public ExceptionBadRequest(ExceptionEnumInterface enumCode) {
        super(enumCode);
    }

    public ExceptionBadRequest(ExceptionEnumInterface enumCode, Object data) {
        super(enumCode, data);
    }
}
