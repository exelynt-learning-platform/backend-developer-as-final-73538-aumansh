package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ReservationResponse;
import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.model.Reservation;
import com.example.bookingsystem.model.Resource;
import org.springframework.beans.BeanUtils;

public class DtoMapper {
    public static ReservationResponse mapToReservationDto(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        BeanUtils.copyProperties(reservation, dto);
        if (reservation.getUser() != null) {
            dto.setUserId(reservation.getUser().getId());
        }
        if (reservation.getResource() != null) {
            dto.setResource(mapToResourceDto(reservation.getResource()));
        }
        return dto;
    }

    public static ResourceDto mapToResourceDto(Resource resource) {
        ResourceDto dto = new ResourceDto();
        BeanUtils.copyProperties(resource, dto);
        return dto;
    }
}
