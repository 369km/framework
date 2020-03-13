package org.example.web.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.business.exception.BaseException;
import org.example.business.exception.ExceptionStatus;

import java.util.Optional;

public class RestResponse<T> extends BaseResponse {
    private T data;

    public RestResponse(T data, ExceptionStatus status) {
        super(status.getCode(), status.getMessage());
        this.data = data;
    }

    public RestResponse(ExceptionStatus status) {
        super(status.getCode(), status.getMessage());
    }

    public RestResponse(T data) {
        this(data, ExceptionStatus.SUCCESS);
    }

    public RestResponse() {
        this(null, ExceptionStatus.SUCCESS);
    }

    public RestResponse(BaseException exceptionV2) {
        super(exceptionV2.getErrorCode(), exceptionV2.getMessage());
        this.data = (T) exceptionV2.getData();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public Optional<T> getOptionalData() {
        return Optional.ofNullable(data);
    }

}
