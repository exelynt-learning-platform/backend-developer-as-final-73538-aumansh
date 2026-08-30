package com.example.bookingsystem.service;

import com.example.bookingsystem.dto.ResourceDto;
import com.example.bookingsystem.model.Resource;
import com.example.bookingsystem.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;


    @Test
    void testCreateResource() {
        ResourceDto dto = new ResourceDto();
        dto.setName("Projector");
        dto.setDescription("Sony Projector");

        Resource resource = new Resource();
        resource.setId(1L);
        resource.setName("Projector");
        resource.setDescription("Sony Projector");

        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        ResourceDto created = resourceService.createResource(dto);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Projector", created.getName());
    }

    @Test
    void testGetResourceById() {
        Resource resource = new Resource();
        resource.setId(1L);
        resource.setName("Projector");

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(resource));

        ResourceDto found = resourceService.getResourceById(1L);

        assertNotNull(found);
        assertEquals("Projector", found.getName());
    }
}
