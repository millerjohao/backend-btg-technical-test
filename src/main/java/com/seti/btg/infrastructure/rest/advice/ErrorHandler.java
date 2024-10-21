package com.seti.btg.infrastructure.rest.advice;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Manejador de errores personalizado para el ambiente
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<String> handleNotFound(ErrorException e){
        return new ResponseEntity<String>(e.getErrorMessage(), e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> notFoundResource(NoHandlerFoundException e){
        return new ResponseEntity<String>(ErrorConstant.ENDPOINT_NOT_IMPLEMENTED, e.getStatusCode());
    }
}
