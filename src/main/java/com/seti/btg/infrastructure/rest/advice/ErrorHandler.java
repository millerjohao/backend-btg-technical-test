package com.seti.btg.infrastructure.rest.advice;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.ErrorResponse;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

/**
 * Manejador de errores personalizado para el ambiente
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ErrorException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode().value(),
                e.getErrorMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorResponse, e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundResource(NoHandlerFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getStatusCode().value(),
                ErrorConstant.ENDPOINT_NOT_IMPLEMENTED,
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }
}
