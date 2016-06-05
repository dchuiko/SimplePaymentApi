package me.dchuiko.spa.rest.exception;

import me.dchuiko.spa.rest.http.Status;

public class ValidationException extends ApplicationException {
    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause, Status.preconditionFailed);
    }

    public ValidationException(String message) {
        super(message, Status.preconditionFailed);
    }
}
