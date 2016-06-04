package me.dchuiko.spa.rest.handler;

import io.vertx.ext.web.RoutingContext;

public interface Handler {
    void readList(RoutingContext context);

    void readId(RoutingContext context);

    void create(RoutingContext context);

    void update(RoutingContext context);

    void delete(RoutingContext context);
}
