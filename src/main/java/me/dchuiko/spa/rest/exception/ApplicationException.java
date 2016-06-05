package me.dchuiko.spa.rest.exception;

import me.dchuiko.spa.rest.http.Status;

public class ApplicationException extends RuntimeException {
    private String message;
    private int status = Status.badRequest;

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, int status) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, Throwable cause, int status) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        if (message != null) {
            return message;
        }

        if (getCause() != null) {
            return getCause().getMessage();
        }

        return super.getMessage();
    }

    public int getStatus() {
        return status;
    }
}
