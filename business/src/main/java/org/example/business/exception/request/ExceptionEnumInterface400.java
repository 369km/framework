package org.example.business.exception.request;

import org.example.business.exception.ExceptionEnumInterface;
import org.springframework.http.HttpStatus;

public interface ExceptionEnumInterface400 extends ExceptionEnumInterface {
    @Override
    default HttpStatus getType() {
        return HttpStatus.BAD_REQUEST;
    }
}
