package me.dchuiko.spa.rest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import me.dchuiko.spa.MainVerticle;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public abstract class BaseRestTest {
    protected Vertx vertx;

    @Before
    public void before(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", 8081));
        vertx.deployVerticle(MainVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

}
