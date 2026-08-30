import os
file_path = "src/main/resources/application.properties"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("jwt.secret=${JWT_SECRET}", "jwt.secret=${JWT_SECRET:Ni0H+ehYYBpa+eJDl/hrxe+sl9Zem1DcOezjtjdS2UQ=}")

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
