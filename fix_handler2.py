import os
filepath = "src/main/java/com/example/bookingsystem/exception/GlobalExceptionHandler.java"
with open(filepath, "r", encoding="utf-8") as f:
    content = f.read()

validation_handler = """
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationHandling(ValidationException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
"""

content = content.replace("public class GlobalExceptionHandler {", "public class GlobalExceptionHandler {" + validation_handler)

with open(filepath, "w", encoding="utf-8") as f:
    f.write(content)
