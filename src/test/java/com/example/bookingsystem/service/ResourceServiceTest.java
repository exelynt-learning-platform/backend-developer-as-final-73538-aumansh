package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.exception.ResourceNotFoundException;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    private Resource resource;
    private ResourceDto resourceDto;

    @BeforeEach
    void setUp() {
        resource = new Resource();
        resource.setId(1L);
        resource.setName("Test Resource");
        resource.setDescription("Test Description");
        resource.setPrice(BigDecimal.TEN);

        resourceDto = new ResourceDto();
        resourceDto.setName("Test Resource");
        resourceDto.setDescription("Test Description");
        resourceDto.setPrice(BigDecimal.TEN);
    }

    @Test
    void createResource_Success() {
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        ResourceDto response = resourceService.createResource(resourceDto);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Resource", response.getName());
    }

    @Test
    void getAllResources_Success() {
        Page<Resource> page = new PageImpl<>(Collections.singletonList(resource));
        when(resourceRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<ResourceDto> response = resourceService.getAllResources(PageRequest.of(0, 10));
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
    }

    @Test
    void getResourceById_Success() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        ResourceDto response = resourceService.getResourceById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getResourceById_NotFound() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> resourceService.getResourceById(1L));
    }

    @Test
    void updateResource_Success() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        ResourceDto response = resourceService.updateResource(1L, resourceDto);
        assertNotNull(response);
        assertEquals("Test Resource", response.getName());
    }

    @Test
    void deleteResource_Success() {
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));
        doNothing().when(resourceRepository).delete(resource);

        assertDoesNotThrow(() -> resourceService.deleteResource(1L));
        verify(resourceRepository, times(1)).delete(resource);
    }
}
