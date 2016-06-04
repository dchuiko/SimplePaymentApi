package me.dchuiko.spa;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import me.dchuiko.spa.json.WebContext;

import static io.vertx.core.Vertx.vertx;
import static me.dchuiko.spa.json.JsonType.jsonTypes;

public class Application {
    private static final String protocol = "http";
    private static final String host = "localhost";
    private static final int port = 8080;

    public static void main(String[] args) {
        final WebContext context = new WebContext(protocol, host, port);

        final Vertx vertx = vertx();
        final HttpServer server = vertx.createHttpServer(new HttpServerOptions().setHost(host).setPort(port));
        final Router router = Router.router(vertx);

        jsonTypes().stream().forEach(jsonType -> {
            router.get("/" + jsonType.namePlural()).handler(jsonType.handler()::readList)
                  .produces(jsonType.produces().toString());
            router.get("/" + jsonType.namePlural() + "/:id").handler(jsonType.handler()::readId)
                  .produces(jsonType.produces().toString());

            router.post("/" + jsonType.namePlural()).handler(BodyHandler.create())
                  .produces(jsonType.produces().toString())
                  .consumes(jsonType.consumes().toString());
            router.post("/" + jsonType.namePlural()).handler(jsonType.handler()::create)
                  .produces(jsonType.produces().toString())
                  .consumes(jsonType.consumes().toString());

            router.put("/" + jsonType.namePlural() + "/:id").handler(BodyHandler.create())
                  .produces(jsonType.produces().toString())
                  .consumes(jsonType.consumes().toString());
            router.put("/" + jsonType.namePlural() + "/:id").handler(jsonType.handler()::update)
                  .produces(jsonType.produces().toString())
                  .consumes(jsonType.consumes().toString());

            router.delete("/" + jsonType.namePlural() + "/:id").handler(jsonType.handler()::delete)
                  .produces(jsonType.produces().toString());
        });

        server.requestHandler(router::accept);

        server.listen(8080);
    }


}

































































