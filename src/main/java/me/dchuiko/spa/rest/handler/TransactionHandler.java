package me.dchuiko.spa.rest.handler;

import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.persistence.Transactions;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.Status;

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
    public void create(RoutingContext context) {
        super.create(context);
    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);
    }

    @Override
    public void readId(RoutingContext context) {
        super.readId(context);
    }

}
