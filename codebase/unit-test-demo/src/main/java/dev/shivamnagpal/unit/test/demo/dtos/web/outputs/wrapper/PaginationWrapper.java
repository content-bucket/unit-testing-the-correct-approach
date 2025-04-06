package dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record PaginationWrapper<T>(
        @JsonInclude(JsonInclude.Include.NON_NULL) T data,
        @JsonInclude(JsonInclude.Include.NON_NULL) PaginationResponse paginationResponse
) {
}
