package me.dchuiko.spa.rest.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.persistence.Accounts;
import me.dchuiko.spa.persistence.Transactions;
import me.dchuiko.spa.persistence.Users;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.ParsedUuid;
import me.dchuiko.spa.rest.http.Status;
import me.dchuiko.spa.rest.http.WebContext;
import me.dchuiko.spa.rest.json.AccountJson;
import me.dchuiko.spa.rest.json.UserJson;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserHandler extends GenericHandler {
    private final Users users;
    private final Accounts accounts;
    private final Transactions transactions;

    public UserHandler(Users users, Accounts accounts, Transactions transactions) {
        this.users = users;
        this.accounts = accounts;
        this.transactions = transactions;
    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            List<UserJson> result = users.list().stream().map(user -> new UserJson(webContext, user)).collect(Collectors.toList());
            future.complete(result);
        }, false, new AsyncResultHandler<List<UserJson>>(context) {
            @Override
            protected void doHandle(List<UserJson> result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void readId(RoutingContext context) {
        super.readId(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            final ParsedUuid id = new ParsedUuid(context.request(), "id");
            User result = users.id(id.uuid());
            future.complete(new UserJson(webContext, result));
        }, false, new AsyncResultHandler<UserJson>(context) {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    public void readTransactions(RoutingContext context) {

    }

    public void readAccounts(RoutingContext context) {
        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            final UUID userId = new ParsedUuid(context.request(), "id").uuid();
            List<AccountJson> result = accounts.userAccounts(userId).stream().map(a -> new AccountJson(webContext, a)).collect(Collectors.toList());
            future.complete(result);
        }, false, new AsyncResultHandler<List<AccountJson>>(context) {
            @Override
            protected void doHandle(List<AccountJson> result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void create(RoutingContext context) {
        super.create(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            UserJson userJson = Json.decodeValue(context.getBodyAsString(), UserJson.class);
            User created = users.create(userJson);
            userJson = new UserJson(webContext, created);

            future.complete(userJson);
        }, false, new AsyncResultHandler<UserJson>(context) {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.created).end(Json.encode(result));
            }
        });
    }

    @Override
    public void update(RoutingContext context) {
        super.update(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            final ParsedUuid id = new ParsedUuid(context.request(), "id");
            UserJson userJson = Json.decodeValue(context.getBodyAsString(), UserJson.class);
            User created = users.update(id.uuid(), userJson);
            userJson = new UserJson(webContext, created);

            future.complete(userJson);
        }, false, new AsyncResultHandler<UserJson>(context) {
            @Override
            protected void doHandle(UserJson result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    protected JsonType getType() {
        return JsonType.User;
    }
}
