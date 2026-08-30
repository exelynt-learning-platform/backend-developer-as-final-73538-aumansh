package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ReservationRequest;
import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.exception.UnauthorizedException;
import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import com.example.bookingsystem.repository.ReservationRepository;
import com.example.bookingsystem.repository.ResourceRepository;
import com.example.bookingsystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ResourceRepository resourceRepository,
                              UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));


        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new com.example.bookingsystem.exception.ValidationException("Start and end time must not be null");
        }
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new com.example.bookingsystem.exception.ValidationException("Start time must be before end time");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setPrice(request.getPrice());
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        return mapToDto(saved);
    }

    public Page<ReservationResponse> getReservations(String username, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Specification<Reservation> spec = ReservationSpecification.filterReservations(user, status, minPrice, maxPrice);

        return reservationRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ReservationResponse updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setStatus(status);
        Reservation updated = reservationRepository.save(reservation);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteReservation(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!isAdminOrOwner(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to delete this reservation");
        }
        reservationRepository.delete(reservation);
    }
    
    public ReservationResponse getReservationById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!isAdminOrOwner(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to view this reservation");
        }
        return mapToDto(reservation);
    }

    
    private boolean isAdminOrOwner(User user, Reservation reservation) {
        if (user == null || reservation == null) return false;
        if (user.getRole() == Role.ROLE_ADMIN) {
            return true;
        }
        return reservation.getUser() != null && user.getId().equals(reservation.getUser().getId());
    }

    private ReservationResponse mapToDto(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser() != null ? reservation.getUser().getId() : null);
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setPrice(reservation.getPrice());
        dto.setStatus(reservation.getStatus());

        ResourceDto resourceDto = new ResourceDto();
        if (reservation.getResource() != null) {
            resourceDto.setId(reservation.getResource().getId());
            resourceDto.setName(reservation.getResource().getName());
            resourceDto.setDescription(reservation.getResource().getDescription());
        }
        dto.setResource(resourceDto);

        return dto;
    }

}
