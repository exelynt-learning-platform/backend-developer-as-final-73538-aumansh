import os

# Create Specification Class
spec_content = """package com.example.bookingsystem.service;

import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.ReservationStatus;
import com.example.bookingsystem.model.Role;
import com.example.bookingsystem.model.User;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReservationSpecification {
    public static Specification<Reservation> filterReservations(User user, ReservationStatus status, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (user.getRole() == Role.ROLE_USER) {
                predicates.add(cb.equal(root.get("user").get("id"), user.getId()));
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
"""
with open("src/main/java/com/example/bookingsystem/service/ReservationSpecification.java", "w", encoding="utf-8") as f:
    f.write(spec_content)

# Update ReservationService
file_path = "src/main/java/com/example/bookingsystem/service/ReservationService.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("throw new IllegalArgumentException(", "throw new com.example.bookingsystem.exception.ValidationException(")
content = content.replace("import jakarta.persistence.criteria.Predicate;", "")

import re
old_spec_logic = """        Specification<Reservation> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (user.getRole() == Role.ROLE_USER) {
                predicates.add(cb.equal(root.get("user").get("id"), user.getId()));
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
        };"""

content = content.replace(old_spec_logic, "        Specification<Reservation> spec = ReservationSpecification.filterReservations(user, status, minPrice, maxPrice);")
with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)

