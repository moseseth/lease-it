package com.allane.leaseadmin.exception;

import com.allane.leaseadmin.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<List<ErrorResponse>> handleSQLException(SQLException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(String.valueOf(ex.getErrorCode()), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<ErrorResponse>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        String message = Objects.requireNonNull(ex.getRootCause()).getMessage();
        if (message.toLowerCase().contains("check_model_year")) {
            errors.add(new ErrorResponse("VALIDATION_ERROR_MODEL_YEAR",
                    "The provided model year does not meet the constraints"));
        } else {
            errors.add(new ErrorResponse("UNKNOWN_CONSTRAINT_ERROR",
                    "Unknown constraint violation."));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponse>> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorResponse> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String errorCode = "VALIDATION_ERROR_" + violation.getPropertyPath().toString().toUpperCase();
            String errorMessage = violation.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);
            errorMessages.add(errorResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }
}
