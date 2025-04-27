package org.spring.authenticationservice.DTO.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Pagination class to represent the pagination information in the API response
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
}
