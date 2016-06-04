package me.dchuiko.spa;

import io.vertx.core.Vertx;

import static io.vertx.core.Vertx.vertx;

public class Application {
    public static void main(String[] args) {
        final Vertx vertx = vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}

































































