package me.dchuiko.spa.rest.exception;

public class ValidationException extends ApplicationException {
    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
