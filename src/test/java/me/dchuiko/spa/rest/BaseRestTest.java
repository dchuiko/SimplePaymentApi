package me.dchuiko.spa.rest;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import me.dchuiko.spa.BaseTest;
import me.dchuiko.spa.MainVerticle;
import me.dchuiko.spa.VertxInstance;
import me.dchuiko.spa.rest.http.MimeType;
import me.dchuiko.spa.rest.http.UriParts;
import me.dchuiko.spa.rest.json.TransactionJson;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import static me.dchuiko.spa.rest.http.WebContext.uri;

public abstract class BaseRestTest extends BaseTest {
    protected Vertx vertx;
    protected HttpClient client;
    private int port = 8081;
    private String host = "localhost";

    @Before
    public void before(TestContext context) throws IOException {
        vertx = VertxInstance.vertx();
        final DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));
        vertx.deployVerticle(MainVerticle.class.getName(), options, context.asyncAssertSuccess());

        client = vertx.createHttpClient();
    }

    protected void get(JsonType type, UUID id, Handler<HttpClientResponse> handler) {
        client.get(port, host, new UriParts(uri(type), id).get())
              .putHeader("content-type", MimeType.application_json.toString())
              .handler(handler)
              .end();
    }

    protected void post(JsonType type, String json, Handler<HttpClientResponse> handler) {
        client.post(port, host, uri(type))
              .putHeader("content-type", MimeType.application_json.toString())
              .putHeader("content-length", "" + json.length())
              .handler(handler)
              .write(json)
              .end();
    }

    protected void assertBody(HttpClientResponse response, Consumer<String> handler) {
        final StringBuilder sb = new StringBuilder();
        response.handler(body -> sb.append(body.toString())).endHandler(event -> handler.accept(sb.toString()));
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

}
