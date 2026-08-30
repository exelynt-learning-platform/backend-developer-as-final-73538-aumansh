package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {
    @org.springframework.data.jpa.repository.Query("SELECT COUNT(r) FROM Reservation r WHERE r.resource.id = :resourceId AND r.status <> :cancelledStatus AND r.startTime < :endTime AND r.endTime > :startTime ")
    long countOverlappingReservations(@org.springframework.data.repository.query.Param("resourceId") Long resourceId,
                                      @org.springframework.data.repository.query.Param("startTime") java.time.LocalDateTime startTime,
                                      @org.springframework.data.repository.query.Param("endTime") java.time.LocalDateTime endTime,
                                      @org.springframework.data.repository.query.Param("cancelledStatus") ReservationStatus cancelledStatus);
    
    
}
