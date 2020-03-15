package org.example.web.interceptor;

import com.google.common.collect.ImmutableMap;
import org.example.common.exception.BaseException;
import org.example.common.exception.ExceptionStatus;
import org.example.common.exception.ValidationError;
import org.example.common.utils.LoggerUtil;
import org.example.web.response.RestResponse;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, BindException.class})
    public ResponseEntity<RestResponse> validationErrorHandler(HttpServletRequest request, Exception e) {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);
        RestResponse cqjcResponse;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();
            List<ValidationError> errors = new LinkedList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            for (ObjectError objectError : objectErrors) {
                errors.add(new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage()));
            }
            cqjcResponse = new RestResponse<>(errors, ExceptionStatus.PARAM_NOT_CORRECT);

        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException ex = (MethodArgumentTypeMismatchException) e;
            cqjcResponse = new RestResponse<>(ImmutableMap.of(ex.getName(), "类型错误"), ExceptionStatus.PARAM_NOT_CORRECT);
        } else {  //BindException
            BindException ex = (BindException) e;
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();
            List<ValidationError> errors = new LinkedList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            for (ObjectError objectError : objectErrors) {
                errors.add(new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage()));
            }
            cqjcResponse = new RestResponse<>(errors, ExceptionStatus.PARAM_NOT_CORRECT);
        }
        return new ResponseEntity<>(cqjcResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse> exceptionHandler(HttpServletRequest request, HttpMessageNotReadableException e) {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);
        RestResponse scResponse = new RestResponse<>(ImmutableMap.of("request", "请求体参数类型错误"), ExceptionStatus.PARAM_NOT_CORRECT);
        return new ResponseEntity<>(scResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<RestResponse> exceptionHandler(HttpServletRequest request, BaseException e) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);
        RestResponse response = new RestResponse(e);
        //内部服务异常情况,映射httpStatus=200,具体业务根据ScResponse.code 进行处理
        return new ResponseEntity<>(response, responseStatus.value());
    }

    /**
     * 处理get 请求参数中,未传参数时的错误处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<RestResponse> missingServletRequestErrorHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);
        RestResponse scResponse = new RestResponse<>(ImmutableMap.of((e).getParameterName(), "参数名输入错误"),ExceptionStatus.PARAM_NOT_CORRECT);
        return new ResponseEntity<>(scResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConversionFailedException.class)
    public ResponseEntity<RestResponse> conversionFailedHandler(HttpServletRequest request, ConversionFailedException e) {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);

        RestResponse scResponse = new RestResponse<>(ImmutableMap.of(e.getValue(), "类型值输入错误"), ExceptionStatus.PARAM_NOT_CORRECT);
        return new ResponseEntity<>(scResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * GET请求 validated 验证异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponse> handleApiConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);

        Map<String, Object> fieldErrorMap = new HashMap<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            PathImpl propertyPath = (PathImpl) (violation).getPropertyPath();
            if (!propertyPath.getLeafNode().asString().matches("arg[0-9]*")) {
                fieldErrorMap.put(propertyPath.getLeafNode().asString(), violation.getMessage());
            }
        }
        RestResponse scResponse = new RestResponse<>(fieldErrorMap, ExceptionStatus.PARAM_NOT_CORRECT);
        return new ResponseEntity<>(scResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        LoggerUtil.logFilter(e);
        fillExceptionInfoToRequest(request, e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        RestResponse scResponse = new RestResponse<>(null, ExceptionStatus.SERVICE_INTERNAL);
        return new ResponseEntity<>(scResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 将异常信息存放在requestScope方便在Interceptor还可以获取到异常信息
     *
     * @param request
     * @param e
     */
    private void fillExceptionInfoToRequest(HttpServletRequest request, Exception e) {
        request.setAttribute("exception", e);
    }
}
