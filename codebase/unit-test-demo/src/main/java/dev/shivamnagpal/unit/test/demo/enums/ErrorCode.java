package dev.shivamnagpal.unit.test.demo.enums;

import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper.ErrorCodeTrait;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode implements ErrorCodeTrait {
    INTERNAL_SERVER_ERROR("001", "Internal Server Error"),
    INPUT_VALIDATION_ERROR("002", "Input Validation Error"),
    NO_HANDLER_FOUND("003", "No Handler Found"),
    FORBIDDEN("004", "Forbidden to access the resource"),
    UNAUTHORIZED("005", "Unauthorized access"),
    INVALID_CREDENTIALS("006", "Invalid Credentials"),
    ;

    public static final String ERROR_CODE_PREFIX = "UTD-E-";

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = ERROR_CODE_PREFIX + code;
        this.message = message;
    }
}
