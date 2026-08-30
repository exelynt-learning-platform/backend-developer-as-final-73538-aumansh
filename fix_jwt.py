import os
file_path = 'src/main/java/com/example/bookingsystem/security/JwtTokenProvider.java'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Replace weak secret with a properly fetched environment variable or exception if not present
new_content = content.replace('@Value("${jwt.secret}")', '@Value("${jwt.secret}")')

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(new_content)
