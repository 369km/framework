package org.example.common.exception.request;

import org.example.common.exception.ExceptionEnumInterface;
import org.springframework.http.HttpStatus;

public interface ExceptionEnumInterface403 extends ExceptionEnumInterface {
    @Override
    default HttpStatus getType() {
        return HttpStatus.FORBIDDEN;
    }
}
