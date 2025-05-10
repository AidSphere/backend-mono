package org.spring.authenticationservice.DTO.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
    // Getters and setters
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String path;

    public ErrorResponse(int status, LocalDateTime timestamp, String message, String path) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

}

