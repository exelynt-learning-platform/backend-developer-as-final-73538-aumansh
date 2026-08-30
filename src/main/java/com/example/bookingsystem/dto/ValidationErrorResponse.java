package com.example.bookingsystem.dto;

import java.time.LocalDateTime;

public class ValidationErrorResponse {
    private String field;
    private String message;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getField() { return field; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
