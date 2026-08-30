import os
file_path = "src/main/java/com/example/bookingsystem/service/ReservationService.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()
content = content.replace("import java.util.stream.Collectors;", "")
with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
