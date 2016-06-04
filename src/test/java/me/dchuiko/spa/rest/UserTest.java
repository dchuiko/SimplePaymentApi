package me.dchuiko.spa.rest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import me.dchuiko.spa.MainVerticle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(VertxUnitRunner.class)
public class UserTest {

    Vertx vertx;

    @Before
    public void before(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8081));
        vertx.deployVerticle(MainVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().getNow(8081, "localhost", "/api/users", response -> {
            context.assertEquals(200, response.statusCode());
            response.handler(body -> {
                context.assertTrue(body.toString().contains("Hello"));
                async.complete();
            });
        });
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

}
