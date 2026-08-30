package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
}
