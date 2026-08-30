import os

file_path = "src/main/java/com/example/bookingsystem/controller/ReservationController.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

# Fix the recursion bug manually
old_getUsername = """    private String getUsername(Authentication authentication) {
        if (authentication == null || getUsername(authentication) == null) {
            throw new org.springframework.security.access.AccessDeniedException("User is not authenticated");
        }
        return getUsername(authentication);
    }"""
new_getUsername = """    private String getUsername(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new org.springframework.security.access.AccessDeniedException("User is not authenticated");
        }
        return authentication.getName();
    }"""
content = content.replace(old_getUsername, new_getUsername)

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
