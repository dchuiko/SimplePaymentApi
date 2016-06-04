package me.dchuiko.spa.rest.handler;

import io.vertx.core.AsyncResult;
import me.dchuiko.spa.rest.exception.ApplicationException;

public abstract class AsyncResultHandler<T> implements io.vertx.core.Handler<AsyncResult<T>> {
    @Override
    public void handle(AsyncResult<T> asyncResult) {
        if (asyncResult.failed()) {
            throw new ApplicationException(asyncResult.cause());
        } else {
            doHandle(asyncResult.result());
        }
    }

    protected abstract void doHandle(T result);
}
