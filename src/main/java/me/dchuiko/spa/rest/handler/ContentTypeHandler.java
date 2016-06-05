package me.dchuiko.spa.rest.handler;

import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.rest.http.MimeType;
import me.dchuiko.spa.rest.http.Status;

public class ContentTypeHandler implements io.vertx.core.Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext event) {
        final String header = event.request().getHeader("content-type");
        if (header == null || !header.equals(MimeType.application_json.toString())) {
            event.response().setStatusCode(Status.notFound).setStatusMessage("Only 'application/json' content-type is supported").end();
        } else {
            event.response().putHeader("content-type", MimeType.application_json.toString());
            event.next();
        }
    }
}
