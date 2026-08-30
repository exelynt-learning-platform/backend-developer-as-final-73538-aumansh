import os

file_path = "src/main/java/com/example/bookingsystem/exception/ValidationException.java"
content = """package com.example.bookingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
"""
with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)

