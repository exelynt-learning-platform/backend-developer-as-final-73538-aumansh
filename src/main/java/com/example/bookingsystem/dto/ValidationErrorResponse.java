package com.example.bookingsystem.dto;

import java.time.LocalDateTime;

@lombok.Getter
public class ValidationErrorResponse {
    private String field;
    private String message;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

            public LocalDateTime getTimestamp() { return timestamp; }
}
