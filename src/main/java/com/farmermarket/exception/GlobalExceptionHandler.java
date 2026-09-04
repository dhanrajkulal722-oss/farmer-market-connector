package com.farmermarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation
        .FieldError;
import org.springframework.web.bind
        .MethodArgumentNotValidException;
import org.springframework.web.bind.annotation
        .ExceptionHandler;
import org.springframework.web.bind.annotation
        .RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ════════════════════════════
    // Handles @Valid failures
    // When validation annotations
    // fail like @NotBlank @Email etc
    // Returns 400 with error details
    // ════════════════════════════
    @ExceptionHandler(
        MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
            handleValidation(
                MethodArgumentNotValidException
                        ex) {

        // Collect all field errors
        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName =
                            ((FieldError) error)
                                    .getField();
                    String message =
                            error
                            .getDefaultMessage();
                    errors.put(
                            fieldName,
                            message);
                });

        // Return 400 with errors map
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    // ════════════════════════════
    // Handles ResourceNotFoundException
    // When user/product not found
    // Returns 404 Not Found
    // ════════════════════════════
    @ExceptionHandler(
        ResourceNotFoundException.class)
    public ResponseEntity<String>
            handleNotFound(
                ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // ════════════════════════════
    // Handles business logic errors
    // Email already exists etc
    // Returns 400 Bad Request
    // ════════════════════════════
    @ExceptionHandler(
        RuntimeException.class)
    public ResponseEntity<String>
            handleRuntime(
                RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // ════════════════════════════
    // Handles all other errors
    // Safety net!
    // Returns 500
    // ════════════════════════════
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>
            handleAll(Exception ex) {

        return ResponseEntity
                .status(HttpStatus
                    .INTERNAL_SERVER_ERROR)
                .body("Something went wrong!" +
                      " Please try again.");
    }
}
