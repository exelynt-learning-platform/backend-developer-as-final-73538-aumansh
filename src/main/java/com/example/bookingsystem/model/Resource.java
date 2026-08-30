package com.example.bookingsystem.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}
