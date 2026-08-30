import os
filepath = "src/main/java/com/example/bookingsystem/security/JwtTokenProvider.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

imports = "import jakarta.annotation.PostConstruct;\n"
content = content.replace("import java.util.Date;", "import java.util.Date;\n" + imports)

# Remove key() method
import re
content = re.sub(r"private Key key\(\) \{.*?\}", "", content, flags=re.DOTALL)

# Add cachedKey and init
init_code = """
    private Key cachedKey;

    @PostConstruct
    public void init() {
        this.cachedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
"""
content = content.replace("private long jwtExpirationDate;", "private long jwtExpirationDate;\n" + init_code)
content = content.replace("signWith(key())", "signWith(cachedKey)")
content = content.replace("setSigningKey(key())", "setSigningKey(cachedKey)")

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
