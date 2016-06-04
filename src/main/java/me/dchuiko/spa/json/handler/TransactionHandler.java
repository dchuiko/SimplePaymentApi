package me.dchuiko.spa.json.handler;

import me.dchuiko.spa.json.JsonType;

public class TransactionHandler extends GenericHandler {
    @Override
    protected JsonType getType() {
        return JsonType.Transaction;
    }
}
