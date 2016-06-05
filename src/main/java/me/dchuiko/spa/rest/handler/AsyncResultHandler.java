package me.dchuiko.spa.rest.handler;

import io.vertx.core.AsyncResult;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.rest.exception.ApplicationException;

public abstract class AsyncResultHandler<T> implements io.vertx.core.Handler<AsyncResult<T>> {
    private final RoutingContext context;

    public AsyncResultHandler(RoutingContext context) {
        this.context = context;
    }

    @Override
    public void handle(AsyncResult<T> asyncResult) {
        if (asyncResult.failed()) {
            context.fail(new ApplicationException(asyncResult.cause()));
            context.next();
        } else {
            doHandle(asyncResult.result());
        }
    }

    protected abstract void doHandle(T result);
}
