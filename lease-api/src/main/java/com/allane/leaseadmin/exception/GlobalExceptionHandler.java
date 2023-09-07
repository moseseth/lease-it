package com.allane.leaseadmin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public String handleSQLException(SQLException ex) {
        return "Database error: " + ex.getMessage();
    }
}
