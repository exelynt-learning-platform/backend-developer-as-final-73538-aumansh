package com.example.bookingsystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceDto {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;
        @jakarta.validation.constraints.NotNull(message = "Price cannot be null")
    @jakarta.validation.constraints.Positive(message = "Price must be positive")
    private BigDecimal price;
}
