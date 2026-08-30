import os
filepath = "src/main/java/com/example/bookingsystem/exception/GlobalExceptionHandler.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

import re
new_generic = """    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception) {
        logger.error("An unexpected error occurred", exception);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("message", "An unexpected error occurred. Please try again later.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }"""
    
content = re.sub(r"@ExceptionHandler\(Exception.class\).*?\}", new_generic, content, flags=re.DOTALL)

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
