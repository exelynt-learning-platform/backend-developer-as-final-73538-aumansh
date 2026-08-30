import os
filepath = "src/main/java/com/example/bookingsystem/controller/ResourceController.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("import java.util.List;\n", "")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
