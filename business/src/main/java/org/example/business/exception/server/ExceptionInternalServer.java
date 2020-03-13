package org.example.business.exception.server;

import org.example.business.exception.BaseException;
import org.example.business.exception.ExceptionEnumInterface;
import org.example.business.exception.ExceptionStatus;

public class ExceptionInternalServer extends BaseException {
    public ExceptionInternalServer(ExceptionStatus status) {
        super(status);
    }

    public ExceptionInternalServer(ExceptionStatus status, Object data) {
        super(status, data);
    }

    public ExceptionInternalServer(ExceptionEnumInterface enumCode) {
        super(enumCode);
    }

    public ExceptionInternalServer(ExceptionEnumInterface enumCode, Object data) {
        super(enumCode, data);
    }
}
