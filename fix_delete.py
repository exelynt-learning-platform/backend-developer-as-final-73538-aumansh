import os
file_path = 'src/main/java/com/example/bookingsystem/service/ReservationService.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Update deleteReservation to check null
content = content.replace('!reservation.getUser().getId().equals(user.getId())', 'reservation.getUser() == null || !reservation.getUser().getId().equals(user.getId())')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)
