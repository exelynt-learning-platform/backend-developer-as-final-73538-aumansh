import os
filepath = "src/main/java/com/example/bookingsystem/service/ReservationService.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("@Service\n@Transactional\npublic class", "@Service\npublic class")
content = content.replace("public ReservationResponse createReservation", "@Transactional\n    public ReservationResponse createReservation")
content = content.replace("public ReservationResponse updateReservationStatus", "@Transactional\n    public ReservationResponse updateReservationStatus")
content = content.replace("public void deleteReservation", "@Transactional\n    public void deleteReservation")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
