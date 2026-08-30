import os
filepath = "src/main/resources/application.properties"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("jwt.secret=${JWT_SECRET:Ni0H+ehYYBpa+eJDl/hrxe+sl9Zem1DcOezjtjdS2UQ=}", "jwt.secret=${JWT_SECRET}")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
