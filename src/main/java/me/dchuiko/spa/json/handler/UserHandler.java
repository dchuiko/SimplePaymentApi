package me.dchuiko.spa.json.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import me.dchuiko.spa.json.JsonType;
import me.dchuiko.spa.json.UserJson;

public class UserHandler extends GenericHandler {
    public UserHandler() {

    }

    @Override
    public void readList(RoutingContext context) {
        super.readList(context);
    }

    @Override
    public void readId(RoutingContext context) {
        super.readId(context);
    }

    @Override
    public void create(RoutingContext context) {
        super.create(context);

        UserJson userJson = Json.decodeValue(context.getBodyAsString(), UserJson.class);
        context.response().setStatusCode(200).end();
    }

    @Override
    public void update(RoutingContext context) {
        super.update(context);
    }

    @Override
    public void delete(RoutingContext context) {
        super.delete(context);
    }

    @Override
    protected JsonType getType() {
        return JsonType.User;
    }
}
