package ru.hofftech.deliveryservice.billing.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hofftech.deliveryservice.billing.exception.FetchingBillingAuditException;
import ru.hofftech.deliveryservice.billing.model.dto.ApiError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.error("Произошла ошибка: {}", e.getMessage());
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.I_AM_A_TEAPOT)
                .errors(List.of(e.getMessage()))
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(FetchingBillingAuditException.class)
    public ResponseEntity<ApiError> handleException(FetchingBillingAuditException e) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(List.of(e.getMessage()))
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}