package me.dchuiko.spa.rest.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.model.Transaction;
import me.dchuiko.spa.persistence.Transactions;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.ParsedUuid;
import me.dchuiko.spa.rest.http.Status;
import me.dchuiko.spa.rest.http.WebContext;
import me.dchuiko.spa.rest.json.TransactionJson;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionHandler extends GenericHandler {
    private final Transactions transactions;

    public TransactionHandler(Transactions transactions) {
        this.transactions = transactions;
    }

    @Override
    protected JsonType getType() {
        return JsonType.Transaction;
    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            List<TransactionJson> result = transactions.list().stream().map(t -> new TransactionJson(webContext, t)).collect(Collectors.toList());
            future.complete(result);
        }, false, new AsyncResultHandler<List<TransactionJson>>(context) {
            @Override
            protected void doHandle(List<TransactionJson> result) {
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
            Transaction result = transactions.id(id.uuid());
            future.complete(new TransactionJson(webContext, result));
        }, false, new AsyncResultHandler<TransactionJson>(context) {
            @Override
            protected void doHandle(TransactionJson result) {
                context.response().setStatusCode(Status.ok).end(Json.encode(result));
            }
        });
    }

    @Override
    public void create(RoutingContext context) {
        super.create(context);

        WebContext webContext = new WebContext(context.request());

        context.vertx().executeBlocking(future -> {
            TransactionJson transactionJson = Json.decodeValue(context.getBodyAsString(), TransactionJson.class);
            Transaction created = transactions.create(transactionJson);
            transactionJson = new TransactionJson(webContext, created);

            future.complete(transactionJson);
        }, false, new AsyncResultHandler<TransactionJson>(context) {
            @Override
            protected void doHandle(TransactionJson result) {
                context.response().setStatusCode(Status.created).end(Json.encode(result));
            }
        });
    }

}
