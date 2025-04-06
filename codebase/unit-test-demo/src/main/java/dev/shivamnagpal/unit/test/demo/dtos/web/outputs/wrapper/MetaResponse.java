package dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.shivamnagpal.unit.test.demo.enums.ResponseStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MetaResponse(
        ResponseStatus status,

        @JsonFormat(pattern = RESPONSE_TIMESTAMP_FORMAT, shape = JsonFormat.Shape.STRING) LocalDateTime timestamp
) {
    public static final String RESPONSE_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}
