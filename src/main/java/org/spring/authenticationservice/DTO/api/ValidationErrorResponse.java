package org.spring.authenticationservice.DTO.api;
import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(int status, LocalDateTime timestamp, String message, String path,
                                   Map<String, String> errors) {
        super(status, timestamp, message, path);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
