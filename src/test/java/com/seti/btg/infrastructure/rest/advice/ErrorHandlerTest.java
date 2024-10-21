package com.seti.btg.infrastructure.rest.advice;

import com.seti.btg.domain.constant.ErrorConstant;
import com.seti.btg.domain.model.ErrorResponse;
import com.seti.btg.infrastructure.adapter.exception.ErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
        ResponseEntity<ErrorResponse> response = errorHandler.handleNotFound(errorException);

        // Assert
        ErrorResponse errorResponse = response.getBody();
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(expectedCode.value(), errorResponse.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testNotFoundResource_NoHandlerFoundException() throws Exception {
        // Arrange
        NoHandlerFoundException noHandlerFoundException = new NoHandlerFoundException("GET", "/non-existent", null);
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = ErrorConstant.ENDPOINT_NOT_IMPLEMENTED;

        // Act
        ResponseEntity<ErrorResponse> response = errorHandler.notFoundResource(noHandlerFoundException);

        // Assert
        ErrorResponse errorResponse = response.getBody();
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(expectedStatus.value(), errorResponse.getStatus());
        assertEquals(expectedStatus, response.getStatusCode());
    }
}
