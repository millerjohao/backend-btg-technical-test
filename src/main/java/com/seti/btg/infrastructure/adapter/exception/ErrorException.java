package com.seti.btg.infrastructure.adapter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorException extends RuntimeException {
    private HttpStatus errorCode;
    private String errorMessage;
}
