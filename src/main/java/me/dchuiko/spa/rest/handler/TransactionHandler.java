package me.dchuiko.spa.rest.handler;

import me.dchuiko.spa.rest.JsonType;

public class TransactionHandler extends GenericHandler {
    @Override
    protected JsonType getType() {
        return JsonType.Transaction;
    }
}
