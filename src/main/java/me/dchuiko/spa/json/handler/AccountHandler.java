package me.dchuiko.spa.json.handler;

import me.dchuiko.spa.json.JsonType;

public class AccountHandler extends GenericHandler {
    @Override
    protected JsonType getType() {
        return JsonType.Account;
    }
}
