import os
filepath = "src/main/java/com/example/bookingsystem/security/SecurityConfig.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("public static PasswordEncoder passwordEncoder()", "public PasswordEncoder passwordEncoder()")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
