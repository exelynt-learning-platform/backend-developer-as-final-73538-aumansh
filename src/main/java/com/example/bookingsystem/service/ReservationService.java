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

    @Transactional
    
    private User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }

    public ReservationResponse createReservation(ReservationRequest request, String username) {
        User user = getCurrentUser(username);
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

    public Page<ReservationResponse> getReservations(String username, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        User user = getCurrentUser(username);

        Specification<Reservation> spec = ReservationSpecification.filterReservations(status, minPrice, maxPrice);
        if (user.getRole() != Role.ROLE_ADMIN) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), user.getId()));
        }

        return reservationRepository.findAll(spec, pageable).map(DtoMapper::mapToReservationDto);
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ReservationResponse updateReservationStatus(Long id, ReservationStatus status) {
        if (status == null) {
            throw new ValidationException("Reservation status cannot be null");
        }
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservation.setStatus(status);
        Reservation updated = reservationRepository.save(reservation);
        return DtoMapper.mapToReservationDto(updated);
    }

    @Transactional
    public void deleteReservation(Long id, String username) {
        User user = getCurrentUser(username);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!isAdminOrOwner(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to delete this reservation");
        }
        reservationRepository.delete(reservation);
    }
    
    public ReservationResponse getReservationById(Long id, String username) {
        User user = getCurrentUser(username);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!isAdminOrOwner(user, reservation)) {
            throw new UnauthorizedException("You do not have permission to view this reservation");
        }
        return DtoMapper.mapToReservationDto(reservation);
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
    
    private boolean isAdminOrOwner(User user, Reservation reservation) {
        if (user == null || reservation == null) return false;
        if (user.getRole() == Role.ROLE_ADMIN) return true;
        if (reservation.getUser() == null || reservation.getUser().getId() == null) return false;
        return user.getId().equals(reservation.getUser().getId());
    }



}
