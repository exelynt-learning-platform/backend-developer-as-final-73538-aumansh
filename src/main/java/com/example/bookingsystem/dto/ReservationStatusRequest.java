package com.example.bookingsystem.dto;

import com.example.bookingsystem.model.ReservationStatus;
import jakarta.validation.constraints.NotNull;

public class ReservationStatusRequest {
    @NotNull(message = "Status cannot be null")
    private ReservationStatus status;

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
