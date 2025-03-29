package org.spring.authenticationservice.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.spring.authenticationservice.DTO.api.Pagination;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.stream.Collectors;

public class ApiUtil {

    // Get pagination object from the page object
    public static Pagination getPagination(Page<?> page) {
        return Pagination.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }

    // Get filters from the filter request object
    public static Map<String, String> getFilters(Object filterRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> filters = objectMapper.convertValue(filterRequest, new TypeReference<>() {});
        // Remove null or empty values
        return filters.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
