package org.example.business.exception;


import org.springframework.http.HttpStatus;

public interface ExceptionEnumInterface {
    int getCode();

    String getMessage();

    int getAppType();

    HttpStatus getType();

    default String getErrorCode() {
        return String.format("%02d%s%03d", getAppType(), getType().value(), getCode());
    }
}