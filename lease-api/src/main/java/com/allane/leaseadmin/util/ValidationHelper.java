package com.allane.leaseadmin.util;

import com.allane.leaseadmin.dto.ValidationErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ValidationHelper {
    public static List<ValidationErrorResponse> handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationErrorResponse> validationErrors = new ArrayList<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String errorCode = "VALIDATION_ERROR_" + fieldError.getField().toUpperCase();
                String errorMessage = fieldError.getDefaultMessage();
                ValidationErrorResponse errorResponse = new ValidationErrorResponse(errorCode, errorMessage);
                validationErrors.add(errorResponse);
            }

            return validationErrors;
        }

        return null;
    }

    public static boolean isValidBirthdateFormat(String dateStr) {
        try {
            if (dateStr != null) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate.parse(dateStr, dateFormatter);
                return true;
            } else {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
