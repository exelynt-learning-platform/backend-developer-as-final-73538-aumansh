package com.example.bookingsystem.service;

import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class ReservationSpecification {
    private ReservationSpecification() {}

    public static Specification<Reservation> filterReservations(ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (user.getRole() == Role.ROLE_USER) {
                predicates.add(cb.equal(root.join("user", jakarta.persistence.criteria.JoinType.LEFT).get("id"), user.getId()));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
