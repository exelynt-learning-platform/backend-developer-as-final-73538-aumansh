package com.example.bookingsystem.dto;

import java.time.LocalDateTime;

@lombok.Getter
@lombok.Setter
public class ErrorResponse {
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

            }
