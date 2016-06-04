package me.dchuiko.spa.json.handler;

import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.json.JsonType;

public abstract class GenericHandler implements Handler {
    private static final int METHOD_NOT_ALLOWED = 405;

    public GenericHandler() {
    }

    @Override
    public void readList(RoutingContext context) {
        if (!getType().canRead()) {
            context.response().setStatusCode(METHOD_NOT_ALLOWED).end();
        }

//        context.vertx().executeBlocking(event -> {
//            try {
//                Thread.currentThread().sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            event.complete("Cool");
//        }, event -> context.response().write(event.result().toString()).setStatusCode(200).end());

    }

    @Override
    public void readId(RoutingContext context) {
        if (!getType().canRead()) {
            context.response().setStatusCode(METHOD_NOT_ALLOWED).end();
        }
    }

    @Override
    public void create(RoutingContext context) {
        if (!getType().canCreate()) {
            context.response().setStatusCode(METHOD_NOT_ALLOWED).end();
        }
    }

    @Override
    public void update(RoutingContext context) {
        if (!getType().canUpdate()) {
            context.response().setStatusCode(METHOD_NOT_ALLOWED).end();
        }
    }

    @Override
    public void delete(RoutingContext context) {
        if (!getType().canDelete()) {
            context.response().setStatusCode(METHOD_NOT_ALLOWED).end();
        }
    }

    protected abstract JsonType getType();
}
