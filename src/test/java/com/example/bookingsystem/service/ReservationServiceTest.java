package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ReservationRequest;
import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import com.example.bookingsystem.repository.ReservationRepository;
import com.example.bookingsystem.repository.ResourceRepository;
import com.example.bookingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    private User user;
    private Resource resource;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole(Role.ROLE_USER);

        resource = new Resource();
        resource.setId(1L);
        resource.setName("Test Resource");
    }

    @Test
    void createReservation_Success() {
        ReservationRequest request = new ReservationRequest();
        request.setResourceId(1L);
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(2));
        request.setPrice(BigDecimal.TAN);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        Reservation saved = new Reservation();
        saved.setId(10L);
        saved.setUser(user);
        saved.setResource(resource);
        saved.setStartTime(request.getStartTime());
        saved.setEndTime(request.getEndTime());
        saved.setPrice(request.getPrice());
        saved.setStatus(ReservationStatus.PENDING);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        ReservationResponse response = reservationService.createReservation(request, "testuser");

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals(ReservationStatus.PENDING, response.getStatus());
    }
}
