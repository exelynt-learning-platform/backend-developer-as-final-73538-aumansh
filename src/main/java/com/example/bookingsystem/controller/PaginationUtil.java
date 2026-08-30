package com.example.bookingsystem.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.bookingsystem.exception.ValidationException;

public class PaginationUtil {
    public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortDir.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(sortBy).descending();
        } else {
            throw new ValidationException("Invalid sort direction. Must be ASC or DESC.");
        }
        return PageRequest.of(page, size, sort);
    }
}
