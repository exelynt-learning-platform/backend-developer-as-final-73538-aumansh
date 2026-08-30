package com.example.bookingsystem.controller;

import com.example.bookingsystem.dto.ReservationRequest;
import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.dto.ReservationStatusRequest;
import com.example.bookingsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.example.bookingsystem.security.AuthUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request,
            Authentication authentication) {
        return new ResponseEntity<>(reservationService.createReservation(request, AuthUtil.getUsername(authentication)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getReservations(
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 10, sort = "id") Pageable pageable,
            Authentication authentication) {

        return ResponseEntity.ok(reservationService.getReservations(AuthUtil.getUsername(authentication), status, minPrice, maxPrice, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(reservationService.getReservationById(id, AuthUtil.getUsername(authentication)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponse> updateReservationStatus(
            @PathVariable Long id,
            @Valid @RequestBody ReservationStatusRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, request.getStatus(), AuthUtil.getUsername(authentication)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, Authentication authentication) {
        reservationService.deleteReservation(id, AuthUtil.getUsername(authentication));
        return ResponseEntity.noContent().build();
    }
}
