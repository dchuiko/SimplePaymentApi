package me.dchuiko.spa;

import io.vertx.core.Vertx;

public class Application {
    public static void main(String[] args) {
        Vertx vertx = VertxInstance.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}

































































