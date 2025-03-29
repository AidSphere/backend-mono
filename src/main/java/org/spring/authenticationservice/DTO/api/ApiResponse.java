package org.spring.authenticationservice.DTO.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//The `ApiResponse` class is a generic data transfer object (DTO) used to standardize the structure of API responses
// in a Spring Boot application. It includes fields for timestamp, status, data, message, error, path, and pagination information.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private T data; // Generic type for the response data
    private String message;
    private String error; // Optional, used only when there's an error
    private String path;
    private Pagination pagination; // Optional, used for paginated responses
}

