package com.example.bookingsystem.controller;

import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    
    @GetMapping
    public ResponseEntity<Page<ResourceDto>> getAllResources(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(resourceService.getAllResources(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResourceDto> createResource(@Valid @RequestBody ResourceDto resourceDto) {
        return new ResponseEntity<>(resourceService.createResource(resourceDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResourceDto> updateResource(@PathVariable Long id, @Valid @RequestBody ResourceDto resourceDto) {
        return ResponseEntity.ok(resourceService.updateResource(id, resourceDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok("Resource deleted successfully");
    }
}
