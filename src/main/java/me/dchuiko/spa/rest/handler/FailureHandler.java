package me.dchuiko.spa.rest.handler;

import io.vertx.core.json.DecodeException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.rest.exception.ApplicationException;
import me.dchuiko.spa.rest.http.MimeType;
import me.dchuiko.spa.rest.http.Status;

public class FailureHandler implements io.vertx.core.Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(FailureHandler.class);

    @Override
    public void handle(RoutingContext handler) {
        final Throwable failure = handler.failure();
        if (failure == null) {
            handler.response().setStatusCode(Status.internalServerError).setStatusMessage("Internal Server Error").end();
        } else {
            logger.error(failure.getMessage());

            if (failure instanceof ApplicationException) {
                ApplicationException ae = (ApplicationException) failure;
                handler.response().setStatusCode(ae.getStatus()).putHeader("content-type", MimeType.application_text.toString()).end(ae.getMessage());
            } else if (failure instanceof DecodeException) {
                handler.response().setStatusCode(400).putHeader("content-type", MimeType.application_text.toString())
                       .end("Wrong format: " + failure.getMessage());
            } else {
                handler.response().setStatusCode(500).end("Unknown Server Error");
            }
        }
    }
}
