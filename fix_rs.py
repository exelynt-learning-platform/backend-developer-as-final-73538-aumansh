import os
file_path = "src/main/java/com/example/bookingsystem/service/ReservationService.java"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

# Add isAdminOrOwner helper
helper_method = """
    private boolean isAdminOrOwner(User user, Reservation reservation) {
        if (user.getRole() == Role.ROLE_ADMIN) {
            return true;
        }
        return reservation.getUser() != null && reservation.getUser().getId().equals(user.getId());
    }
"""

content = content.replace("private ReservationResponse mapToDto(Reservation reservation)", helper_method + "\n    private ReservationResponse mapToDto(Reservation reservation)")

content = content.replace("if (user.getRole() == Role.ROLE_USER && (reservation.getUser() == null || !reservation.getUser().getId().equals(user.getId()))) {", "if (!isAdminOrOwner(user, reservation)) {")

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
