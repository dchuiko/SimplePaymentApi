package me.dchuiko.spa.rest.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.model.AccountWithBalance;
import me.dchuiko.spa.persistence.Accounts;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.Status;
import me.dchuiko.spa.rest.http.WebContext;
import me.dchuiko.spa.rest.json.AccountJson;

import java.util.List;
import java.util.stream.Collectors;

public class AccountHandler extends GenericHandler {
    private final Accounts accounts;

    public AccountHandler(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);
        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            List<AccountJson> result = accounts.list().stream().map(account -> new AccountJson(webContext, account)).collect(Collectors.toList());
            future.complete(result);
        }, false, new AsyncResultHandler<List<AccountJson>>(context) {
            @Override
            protected void doHandle(List<AccountJson> result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void readId(RoutingContext context) {
        super.readId(context);
    }

    @Override
    public void create(RoutingContext context) {
        super.create(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            AccountJson accountJson = Json.decodeValue(context.getBodyAsString(), AccountJson.class);
            AccountWithBalance created = accounts.create(accountJson);
            accountJson = new AccountJson(webContext, created);

            future.complete(accountJson);
        }, false, new AsyncResultHandler<AccountJson>(context) {
            @Override
            protected void doHandle(AccountJson result) {
                context.response().setStatusCode(Status.created).end(Json.encode(result));
            }
        });
    }

    @Override
    public void update(RoutingContext context) {
        super.update(context);
    }

    @Override
    protected JsonType getType() {
        return JsonType.Account;
    }
}
