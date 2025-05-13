package com.codeid.eshopay_backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.NoHandlerFoundException;

import com.codeid.eshopay_backend.model.response.ApiError;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {    

       @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException exc) {
        
        List<String> errorMessages = exc.getBindingResult()
                                        .getAllErrors()
                                        .stream()
                                        .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                                        .collect(Collectors.toList());
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,  
                "Validation failed", 
                errorMessages
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    
  @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleidNotFound(
            NoHandlerFoundException exc) {
        
                ApiError error = new ApiError(
                    HttpStatus.NOT_FOUND,  
                    "Route not found", 
                    Collections.singletonList(exc.getMessage()) 
            );
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred",
                Collections.singletonList(ex.getMessage()));
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
