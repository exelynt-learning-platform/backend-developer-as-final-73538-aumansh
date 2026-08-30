package com.example.bookingsystem.dto;

import com.example.bookingsystem.model.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private Long userId;
    private ResourceDto resource;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
    private BigDecimal price;
}
