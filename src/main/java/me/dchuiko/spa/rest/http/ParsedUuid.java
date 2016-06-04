package me.dchuiko.spa.rest.http;

import io.vertx.core.http.HttpServerRequest;
import me.dchuiko.spa.rest.exception.ApplicationException;

import java.util.UUID;

public class ParsedUuid {
    private final String uuidAsString;
    private final String paramName;

    public ParsedUuid(HttpServerRequest request, String paramName) {
        this.uuidAsString = request.getParam(paramName);
        this.paramName = paramName;
    }

    public UUID uuid() {
        try {
            return UUID.fromString(uuidAsString);
        } catch (Exception e) {
            throw new ApplicationException("Parameter '" + paramName + "' is not uuid", e, Status.preconditionFailed);
        }
    }
}
