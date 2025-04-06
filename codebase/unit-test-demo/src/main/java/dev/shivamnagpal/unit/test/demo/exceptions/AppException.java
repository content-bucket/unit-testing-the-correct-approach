package dev.shivamnagpal.unit.test.demo.exceptions;

public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
