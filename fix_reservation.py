import os
file_path = "src/main/java/com/example/bookingsystem/service/ReservationService.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

# Fix the condition precedence
content = content.replace("reservation.getUser() == null || !reservation.getUser().getId().equals(user.getId())", "(reservation.getUser() == null || !reservation.getUser().getId().equals(user.getId()))")

# Fix the missing closing brace
if not content.strip().endswith("}"):
    content = content + "\n}\n"
else:
    # Check if we need one more
    open_braces = content.count("{")
    close_braces = content.count("}")
    if open_braces > close_braces:
        content = content + ("\n}" * (open_braces - close_braces)) + "\n"

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
