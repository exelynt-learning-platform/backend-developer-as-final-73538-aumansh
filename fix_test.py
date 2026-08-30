import os
filepath = "src/test/java/com/example/bookingsystem/service/ResourceServiceTest.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

content = content.replace("class ResourceServiceTest", "@org.junit.jupiter.api.extension.ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)\nclass ResourceServiceTest")
content = content.replace("    @BeforeEach\n    void setUp() {\n        MockitoAnnotations.openMocks(this);\n    }\n", "")
content = content.replace("import org.mockito.MockitoAnnotations;\n", "")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
