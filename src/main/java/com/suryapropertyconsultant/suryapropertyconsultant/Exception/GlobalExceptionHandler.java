//package com.suryapropertyconsultant.suryapropertyconsultant.Exception;
//
//public class GlobalExceptionHandler {
//}
// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Exception/GlobalExceptionHandler.java
package com.suryapropertyconsultant.suryapropertyconsultant.Exception; // Or wherever you put your exceptions

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // This annotation makes it a global exception handler
public class GlobalExceptionHandler {

    // Handles validation errors (e.g., @NotBlank, @NotNull, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Return 400 Bad Request
    }

    // You can add more handlers for other exceptions like:
    // @ExceptionHandler(NoSuchElementException.class)
    // public ResponseEntity<String> handleNotFoundException(NoSuchElementException ex) {
    //     return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    // }

    // Generic exception handler (consider logging the full stack trace for this one)
    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<String> handleGenericException(Exception ex) {
    //     // Log the exception: logger.error("An unexpected error occurred", ex);
    //     return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }
}
