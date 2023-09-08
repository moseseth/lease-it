package com.allane.leaseadmin.unit.util;

import com.allane.leaseadmin.dto.ValidationErrorResponse;
import com.allane.leaseadmin.util.ValidationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationHelperTest {

    @Test
    public void testHandleValidationErrors_WithErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName", "fieldName1", "Error message 1"));
        fieldErrors.add(new FieldError("objectName", "fieldName2", "Error message 2"));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        List<ValidationErrorResponse> validationErrors = ValidationHelper.handleValidationErrors(bindingResult);

        assertNotNull(validationErrors);
        assertEquals(2, validationErrors.size());
        assertEquals("VALIDATION_ERROR_FIELDNAME1", validationErrors.get(0).errorCode());
        assertEquals("Error message 1", validationErrors.get(0).errorMessage());
        assertEquals("VALIDATION_ERROR_FIELDNAME2", validationErrors.get(1).errorCode());
        assertEquals("Error message 2", validationErrors.get(1).errorMessage());
    }

    @Test
    public void testHandleValidationErrors_NoErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        List<ValidationErrorResponse> validationErrors = ValidationHelper.handleValidationErrors(bindingResult);

        assertNull(validationErrors);
    }

    @Test
    public void testIsValidBirthdateFormat_ValidDate() {
        String validDateStr = "2022-12-31";

        boolean isValid = ValidationHelper.isValidBirthdateFormat(validDateStr);

        assertTrue(isValid);
    }

    @Test
    public void testIsValidBirthdateFormat_NullDate() {
        String nullDateStr = null;

        boolean isValid = ValidationHelper.isValidBirthdateFormat(nullDateStr);

        assertFalse(isValid);
    }

    @Test
    public void testIsValidBirthdateFormat_InvalidDate() {
        String invalidDateStr = "2022/12/31";

        boolean isValid = ValidationHelper.isValidBirthdateFormat(invalidDateStr);

        assertFalse(isValid);
    }
}

