package com.example.bookingsystem.repository;

import com.example.bookingsystem.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
