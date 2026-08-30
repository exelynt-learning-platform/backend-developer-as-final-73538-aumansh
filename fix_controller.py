import os
file_path = 'src/main/java/com/example/bookingsystem/controller/ResourceController.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

new_get = '''
    @GetMapping
    public ResponseEntity<Page<ResourceDto>> getAllResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        org.springframework.data.domain.Sort sort = sortDir.equalsIgnoreCase(org.springframework.data.domain.Sort.Direction.ASC.name()) ? org.springframework.data.domain.Sort.by(sortBy).ascending()
                : org.springframework.data.domain.Sort.by(sortBy).descending();
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);
        return ResponseEntity.ok(resourceService.getAllResources(pageable));
    }
'''

import re
content = re.sub(r'@GetMapping\s+public ResponseEntity<List<ResourceDto>> getAllResources\(\) \{[^\}]+\}', new_get, content)
content = content.replace('import java.util.List;', 'import java.util.List;\nimport org.springframework.data.domain.Page;')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)
