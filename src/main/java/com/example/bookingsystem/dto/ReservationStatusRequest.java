package com.example.bookingsystem.dto;

import com.example.bookingsystem.model.ReservationStatus;
import jakarta.validation.constraints.NotNull;

@lombok.Getter
@lombok.Setter
public class ReservationStatusRequest {
    @NotNull(message = "Status cannot be null")
    private ReservationStatus status;

    
    }
