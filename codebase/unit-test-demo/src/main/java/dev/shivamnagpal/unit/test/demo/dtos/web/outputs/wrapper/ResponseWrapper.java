package dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.shivamnagpal.unit.test.demo.enums.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseWrapper<T>(
        @JsonInclude(JsonInclude.Include.NON_NULL) T data,
        @JsonInclude(JsonInclude.Include.NON_NULL) PaginationResponse pagination,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<ErrorResponse> errors,
        MetaResponse meta
) {

    public static <T> ResponseWrapper<T> success(T data) {
        return success(data, null);
    }

    public static <T> ResponseWrapper<T> success(T data, PaginationResponse pagination) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseWrapper<>(data, pagination, null, metaResponse);
    }

    public static <T> ResponseWrapper<T> failure(List<ErrorResponse> errorResponses) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.FAILURE)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseWrapper<>(null, null, errorResponses, metaResponse);
    }

}
