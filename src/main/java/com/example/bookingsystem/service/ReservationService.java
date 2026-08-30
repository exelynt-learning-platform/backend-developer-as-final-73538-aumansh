package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ReservationRequest;
import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.exception.UnauthorizedException;
import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import com.example.bookingsystem.exception.ValidationException;
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

    public ReservationResponse createReservation(ReservationRequest request, String username) {
        User user = fetchUserForUsername(username);
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        validateReservationTimes(request);
        validateOverlap(request);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());
        reservation.setPrice(request.getPrice());
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);
        return DtoMapper.mapToReservationDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponse> getReservations(String username, boolean isAdmin, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Reservation> spec = ReservationSpecification.filterReservations(status, minPrice, maxPrice);
                
        if (!isAdmin) {
            User user = fetchUserForUsername(username);
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("user").get("id"), user.getId()));
        }

        return reservationRepository.findAll(spec, pageable).map(DtoMapper::mapToReservationDto);
    }

    @Transactional
    public ReservationResponse updateReservationStatus(Long id, ReservationStatus status, String username) {
        if (status == null) {
            throw new ValidationException("Reservation status cannot be null");
        }
        User user = fetchUserForUsername(username);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
                
        if (!hasPermission(user, reservation)) {
            throw new UnauthorizedException("You do not have permission for this reservation");
        }
        if (!isAdmin(user) && status != ReservationStatus.CANCELLED) {
            throw new UnauthorizedException("You can only cancel your own reservations");
        }
        
        reservation.setStatus(status);
        Reservation updated = reservationRepository.save(reservation);
        return DtoMapper.mapToReservationDto(updated);
    }

    @Transactional
    public void deleteReservation(Long id, String username) {
        User user = fetchUserForUsername(username);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!hasPermission(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to delete this reservation");
        }
        reservationRepository.delete(reservation);
    }

    public ReservationResponse getReservationById(Long id, String username) {
        User user = fetchUserForUsername(username);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!hasPermission(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to view this reservation");
        }
        return DtoMapper.mapToReservationDto(reservation);
    }

    private User fetchUserForUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }

    private void validateReservationTimes(ReservationRequest request) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new ValidationException("Start and end time must not be null");
        }
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new ValidationException("Start time must be before end time");
        }
    }

    private void validateOverlap(ReservationRequest request) {
        if (reservationRepository.countOverlappingReservations(request.getResourceId(), request.getStartTime(), request.getEndTime(), ReservationStatus.CANCELLED) > 0) {
            throw new ValidationException("Reservation overlaps with an existing booking");
        }
    }

    private boolean isAdmin(User user) {
        return user != null && user.getRole() == com.example.bookingsystem.model.Role.ROLE_ADMIN;
    }

    private boolean hasPermission(User user, Reservation reservation) {
        if (user == null || reservation == null) return false;
        if (isAdmin(user)) return true;
        if (reservation.getUser() == null || reservation.getUser().getId() == null) return false;
        return user.getId().equals(reservation.getUser().getId());
    }
}
