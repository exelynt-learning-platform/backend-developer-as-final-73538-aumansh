import os
file_path = "src/main/java/com/example/bookingsystem/controller/ResourceController.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("import org.springframework.data.domain.Page;", "import org.springframework.data.domain.Page;\nimport org.springframework.data.domain.Pageable;\nimport org.springframework.data.domain.Sort;\nimport org.springframework.data.domain.PageRequest;")
content = content.replace("org.springframework.data.domain.Sort sort", "Sort sort")
content = content.replace("org.springframework.data.domain.Sort.Direction.ASC.name()", "Sort.Direction.ASC.name()")
content = content.replace("org.springframework.data.domain.Sort.by", "Sort.by")
content = content.replace("org.springframework.data.domain.Pageable pageable", "Pageable pageable")
content = content.replace("org.springframework.data.domain.PageRequest.of", "PageRequest.of")

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
