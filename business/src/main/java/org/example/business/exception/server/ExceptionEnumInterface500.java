package org.example.business.exception.server;

import org.example.business.exception.ExceptionEnumInterface;
import org.springframework.http.HttpStatus;

public interface ExceptionEnumInterface500 extends ExceptionEnumInterface {
    @Override
    default HttpStatus getType() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
