package me.dchuiko.spa;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;

public class VertxInstance {
    public static Vertx vertx() {
        final Vertx vertx = Vertx.vertx();
        Json.mapper.registerModule(new JavaTimeModule());
        return vertx;
    }
}
