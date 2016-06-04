package me.dchuiko.spa.rest.handler;

import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.exception.ApplicationException;

import static me.dchuiko.spa.rest.http.Status.methodNotAllowed;

public abstract class GenericHandler implements Handler {


    public GenericHandler() {
    }

    @Override
    public void readList(RoutingContext context) {
        if (!getType().canRead()) {
            throw new ApplicationException("Method 'GET' is not allowed for '" + getType().name() + "'", methodNotAllowed);
        }
    }

    @Override
    public void readId(RoutingContext context) {
        if (!getType().canRead()) {
            throw new ApplicationException("Method 'GET' is not allowed for '" + getType().name() + "'", methodNotAllowed);
        }
    }

    @Override
    public void create(RoutingContext context) {
        if (!getType().canCreate()) {
            throw new ApplicationException("Method 'POST' is not allowed for '" + getType().name() + "'", methodNotAllowed);
        }
    }

    @Override
    public void update(RoutingContext context) {
        if (!getType().canUpdate()) {
            throw new ApplicationException("Method 'PUT' is not allowed for '" + getType().name() + "'", methodNotAllowed);
        }
    }

    @Override
    public void delete(RoutingContext context) {
        if (!getType().canDelete()) {
            throw new ApplicationException("Method 'DELETE' is not allowed for '" + getType().name() + "'", methodNotAllowed);
        }
    }

    protected abstract JsonType getType();
}
