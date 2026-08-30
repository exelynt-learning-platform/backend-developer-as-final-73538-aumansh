package com.example.bookingsystem.controller;

import com.example.bookingsystem.dto.ReservationRequest;
import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {


        return authentication.getName();
    }


    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request,
            Authentication authentication) {
        return new ResponseEntity<>(reservationService.createReservation(request, com.example.bookingsystem.security.AuthUtil.getUsername(authentication)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getReservations(
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Authentication authentication) {
        
        Pageable pageable = PaginationUtil.createPageable(page, size, sortBy, sortDir);

        return ResponseEntity.ok(reservationService.getReservations(com.example.bookingsystem.security.AuthUtil.getUsername(authentication), status, minPrice, maxPrice, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(reservationService.getReservationById(id, com.example.bookingsystem.security.AuthUtil.getUsername(authentication)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponse> updateReservationStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id, Authentication authentication) {
        reservationService.deleteReservation(id, com.example.bookingsystem.security.AuthUtil.getUsername(authentication));
        return ResponseEntity.ok("Reservation deleted successfully");
    }
}
