import os
file_path = 'src/main/java/com/example/bookingsystem/service/ReservationService.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Add @Transactional
content = content.replace('import org.springframework.stereotype.Service;', 'import org.springframework.stereotype.Service;\nimport org.springframework.transaction.annotation.Transactional;')
content = content.replace('@Service\npublic class ReservationService', '@Service\n@Transactional\npublic class ReservationService')

# Null check for mapToDto
mapToDto_new = '''private ReservationResponse mapToDto(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser() != null ? reservation.getUser().getId() : null);
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setPrice(reservation.getPrice());
        dto.setStatus(reservation.getStatus());

        ResourceDto resourceDto = new ResourceDto();
        if (reservation.getResource() != null) {
            resourceDto.setId(reservation.getResource().getId());
            resourceDto.setName(reservation.getResource().getName());
            resourceDto.setDescription(reservation.getResource().getDescription());
        }
        dto.setResource(resourceDto);

        return dto;
    }'''

import re
content = re.sub(r'private ReservationResponse mapToDto\(Reservation reservation\) \{.*\}', mapToDto_new, content, flags=re.DOTALL)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)
