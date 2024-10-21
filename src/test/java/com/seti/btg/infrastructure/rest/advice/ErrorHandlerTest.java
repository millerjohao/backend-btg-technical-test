package com.seti.btg.infrastructure.rest.advice;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Mock
    private ErrorException errorException;

    @Test
    public void testHandleNotFound_ErrorException() {
        // Arrange
        String expectedMessage = "Error occurred";
        HttpStatus expectedCode = HttpStatus.BAD_REQUEST;
        when(errorException.getErrorMessage()).thenReturn(expectedMessage);
        when(errorException.getErrorCode()).thenReturn(expectedCode);

        // Act
        ResponseEntity<String> response = errorHandler.handleNotFound(errorException);

        // Assert
        assertEquals(expectedMessage, response.getBody());
        assertEquals(expectedCode, response.getStatusCode());
    }

    @Test
    public void testNotFoundResource_NoHandlerFoundException() throws Exception {
        // Arrange
        NoHandlerFoundException noHandlerFoundException = new NoHandlerFoundException("GET", "/non-existent", null);

        // Act
        ResponseEntity<String> response = errorHandler.notFoundResource(noHandlerFoundException);

        // Assert
        assertEquals(ErrorConstant.ENDPOINT_NOT_IMPLEMENTED, response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}