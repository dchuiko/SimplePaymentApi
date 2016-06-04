package me.dchuiko.spa.rest.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.persistence.Users;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.ParsedUuid;
import me.dchuiko.spa.rest.http.Status;
import me.dchuiko.spa.rest.json.UserJson;

import java.util.List;
import java.util.stream.Collectors;

public class UserHandler extends GenericHandler {
    private final Users users;

    public UserHandler(Users users) {
        this.users = users;
    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);

        context.vertx().executeBlocking(future -> {
            List<UserJson> result = users.list().stream().map(user -> new UserJson(user, context.request().absoluteURI())).collect(Collectors.toList());
            future.complete(result);
        }, false, new AsyncResultHandler<List<UserJson>>() {
            @Override
            protected void doHandle(List<UserJson> result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void readId(RoutingContext context) {
        super.readId(context);

        context.vertx().executeBlocking(future -> {
            final ParsedUuid id = new ParsedUuid(context.request(), "id");
            User result = users.id(id.uuid());
            future.complete(new UserJson(result, context.request().absoluteURI()));
        }, false, new AsyncResultHandler<UserJson>() {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    public void readTransactions(RoutingContext context) {
    }

    public void readAccounts(RoutingContext context) {
    }

    @Override
    public void create(RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            UserJson userJson = Json.decodeValue(context.getBodyAsString(), UserJson.class);
            User created = users.create(userJson);
            userJson = new UserJson(created, context.request().absoluteURI());

            future.complete(userJson);
        }, false, new AsyncResultHandler<UserJson>() {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.created).end(Json.encode(result));
            }
        });
    }

    @Override
    public void update(RoutingContext context) {
        super.update(context);

        context.vertx().executeBlocking(future -> {
            final ParsedUuid id = new ParsedUuid(context.request(), "id");
            UserJson userJson = Json.decodeValue(context.getBodyAsString(), UserJson.class);
            User created = users.update(id.uuid(), userJson);
            userJson = new UserJson(created, context.request().absoluteURI());

            future.complete(userJson);
        }, false, new AsyncResultHandler<UserJson>() {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void delete(RoutingContext context) {
        super.delete(context);

        context.response().setStatusCode(Status.methodNotAllowed).end();
    }

    @Override
    protected JsonType getType() {
        return JsonType.User;
    }
}
