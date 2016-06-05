package me.dchuiko.spa.rest;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import me.dchuiko.spa.rest.http.MimeType;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class UserTest extends BaseRestTest {



    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();
        vertx.createHttpClient().get(8081, "localhost", "/api/users").putHeader("content-type", MimeType.application_json.toString())
             .handler(response -> {
                context.assertEquals(200, response.statusCode());
                response.handler(body -> {
                    context.assertTrue(body.toString().contains("Hello"));
                    async.complete();
                });

        }).end();
    }



}
