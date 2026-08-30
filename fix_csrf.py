import os
file_path = "src/main/java/com/example/bookingsystem/security/SecurityConfig.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

import re
content = re.sub(r"http\.csrf\(csrf -> csrf\.disable\(\)\)\s*\.authorizeHttpRequests", "http.authorizeHttpRequests", content)

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
