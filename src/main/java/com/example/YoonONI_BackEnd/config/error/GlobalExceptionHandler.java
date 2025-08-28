package com.example.YoonONI_BackEnd.config.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleRestApiException(RestApiException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        log.error("Custom exception occurred: {}", errorCode.name());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(CommonErrorCode.INVALID_PARAMETER.name())
                .message(CommonErrorCode.INVALID_PARAMETER.getMessage())
                .errors(validationErrors)
                .build();

        return new ResponseEntity<>(errorResponse, CommonErrorCode.INVALID_PARAMETER.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected exception occurred", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(CommonErrorCode.INTERNAL_SERVER_ERROR.name())
                .message(CommonErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(TooManyResultsException.class)
    public ResponseEntity<ErrorResponse> handleTooManyResultsException(TooManyResultsException ex) {
        log.error("TooManyResultsException 발생", ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(CommonErrorCode.DUPLICATE_DATA.name())
                .message("결과가 여러 건입니다. 예상과 다릅니다.")
                .build();
        return new ResponseEntity<>(errorResponse, CommonErrorCode.DUPLICATE_DATA.getHttpStatus());
    }
}
