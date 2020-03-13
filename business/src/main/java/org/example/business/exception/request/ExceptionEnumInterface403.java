package org.example.business.exception.request;

import org.example.business.exception.ExceptionEnumInterface;
import org.springframework.http.HttpStatus;

public interface ExceptionEnumInterface403 extends ExceptionEnumInterface {
    @Override
    default HttpStatus getType() {
        return HttpStatus.FORBIDDEN;
    }
}
