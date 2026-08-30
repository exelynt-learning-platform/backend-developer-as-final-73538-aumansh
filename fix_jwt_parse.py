import os
filepath = "src/main/java/com/example/bookingsystem/security/JwtTokenProvider.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace(".parse(token);", ".parseClaimsJws(token);")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
