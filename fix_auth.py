import os
filepath = "src/main/java/com/example/bookingsystem/controller/ReservationController.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

# Replace authentication.getName() with a safe extraction
safe_auth = """    private String getUsername(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new org.springframework.security.access.AccessDeniedException("User is not authenticated");
        }
        return authentication.getName();
    }
"""

content = content.replace("public class ReservationController {", "public class ReservationController {\n\n" + safe_auth)
content = content.replace("authentication.getName()", "getUsername(authentication)")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
