package com.example.bookingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    @NotNull(message = "Resource ID cannot be null")
    private Long resourceId;

    @NotNull(message = "Start time is required and must be in ISO format")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required and must be in ISO format")
    private LocalDateTime endTime;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
}
