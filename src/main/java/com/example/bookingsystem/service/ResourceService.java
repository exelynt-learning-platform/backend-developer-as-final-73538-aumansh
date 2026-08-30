package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ResourceDto createResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        resource.setName(resourceDto.getName());
        resource.setDescription(resourceDto.getDescription());
        Resource saved = resourceRepository.save(resource);
        return mapToDto(saved);
    }

    public List<ResourceDto> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ResourceDto getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return mapToDto(resource);
    }

    public ResourceDto updateResource(Long id, ResourceDto resourceDto) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        resource.setName(resourceDto.getName());
        resource.setDescription(resourceDto.getDescription());
        Resource updated = resourceRepository.save(resource);
        return mapToDto(updated);
    }

    public void deleteResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        resourceRepository.delete(resource);
    }

    private ResourceDto mapToDto(Resource resource) {
        ResourceDto dto = new ResourceDto();
        dto.setId(resource.getId());
        dto.setName(resource.getName());
        dto.setDescription(resource.getDescription());
        return dto;
    }
}
