import os
file_path = 'src/main/java/com/example/bookingsystem/service/ResourceService.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

new_get = '''
    public Page<ResourceDto> getAllResources(Pageable pageable) {
        return resourceRepository.findAll(pageable).map(this::mapToDto);
    }
'''

import re
content = re.sub(r'public List<ResourceDto> getAllResources\(\) \{[^\}]+\}', new_get, content)
content = content.replace('import java.util.List;', 'import java.util.List;\nimport org.springframework.data.domain.Page;\nimport org.springframework.data.domain.Pageable;')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)
