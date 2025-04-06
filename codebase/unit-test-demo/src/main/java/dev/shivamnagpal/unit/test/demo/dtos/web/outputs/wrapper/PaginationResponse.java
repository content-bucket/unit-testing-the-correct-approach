package dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper;

import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PaginationResponse(
        Integer pageNumber, Integer pageSize, Long totalElements,
        Integer totalPages
) {
    public static <T> PaginationResponse from(Page<T> page) {
        return PaginationResponse.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageNumber(page.getNumber() + 1)
                .pageSize(page.getSize())
                .build();
    }
}
