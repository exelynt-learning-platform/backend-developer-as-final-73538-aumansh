import os
file_path = 'src/main/java/com/example/bookingsystem/security/JwtTokenProvider.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

content = content.replace('@Value("")\n    private String jwtSecret;', '@Value("\\")\n    private String jwtSecret;')
content = content.replace('@Value("")\n    private long jwtExpirationDate;', '@Value("\\")\n    private long jwtExpirationDate;')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)
