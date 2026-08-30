import os

util_code = """package com.example.bookingsystem.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }
}
"""

with open("src/main/java/com/example/bookingsystem/controller/PaginationUtil.java", "w", encoding="utf-8") as f:
    f.write(util_code)

def fix_controller(filepath):
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    
    import re
    content = re.sub(r"Sort sort = sortDir.*?PageRequest.of\(page, size, sort\);", "Pageable pageable = PaginationUtil.createPageable(page, size, sortBy, sortDir);", content, flags=re.DOTALL)
    
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)

fix_controller("src/main/java/com/example/bookingsystem/controller/ReservationController.java")
fix_controller("src/main/java/com/example/bookingsystem/controller/ResourceController.java")

