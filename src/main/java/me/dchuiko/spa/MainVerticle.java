package me.dchuiko.spa;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.DecodeException;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import me.dchuiko.spa.persistence.IdGenerator;
import me.dchuiko.spa.persistence.Users;
import me.dchuiko.spa.rest.exception.ApplicationException;
import me.dchuiko.spa.rest.handler.AccountHandler;
import me.dchuiko.spa.rest.handler.TransactionHandler;
import me.dchuiko.spa.rest.handler.UserHandler;
import me.dchuiko.spa.rest.http.MimeType;
import me.dchuiko.spa.rest.http.Status;

import static me.dchuiko.spa.rest.JsonType.Account;
import static me.dchuiko.spa.rest.JsonType.Transaction;
import static me.dchuiko.spa.rest.JsonType.User;

public class MainVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

    private static final String host = "localhost";
    private static final int port = 8080;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final HttpServerOptions serverOptions =
                new HttpServerOptions().setHost(config().getString("http.host", host)).setPort(config().getInteger("http.port", port));
        final HttpServer server = vertx.createHttpServer(serverOptions);
        final Router router = Router.router(vertx);
        final IdGenerator idGenerator = IdGenerator.generator();

        router.route().handler(event -> {
            final String header = event.request().getHeader("content-type");
            if (header == null || !header.equals(MimeType.application_json.toString())) {
                event.response().setStatusCode(Status.notFound).setStatusMessage("Only 'application/json' content-type is supported").end();
            } else {
                event.response().putHeader("content-type", MimeType.application_json.toString());
                event.next();
            }
        });

        Users users = new Users(idGenerator);
        UserHandler userHandler = new UserHandler(users);
        router.route("/api/" + User.namePlural()).handler(BodyHandler.create());
        router.get("/api/" + User.namePlural()).handler(userHandler::readList);
        router.get("/api/" + User.namePlural() + "/:id").handler(userHandler::readId);
        router.get("/api/" + User.namePlural() + "/:id/transactions").handler(userHandler::readTransactions);
        router.get("/api/" + User.namePlural() + "/:id/accounts").handler(userHandler::readTransactions);
        router.post("/api/" + User.namePlural()).handler(userHandler::create);
        router.put("/api/" + User.namePlural() + "/:id").handler(userHandler::update);
        router.delete("/api/" + User.namePlural() + "/:id").handler(userHandler::delete);

        AccountHandler accountHandler = new AccountHandler();
        router.route("/api/" + Account.namePlural()).handler(BodyHandler.create());
        router.get("/api/" + Account.namePlural()).handler(accountHandler::readList);
        router.get("/api/" + Account.namePlural() + "/:id").handler(accountHandler::readId);
        router.post("/api/" + Account.namePlural()).handler(accountHandler::create);
        router.put("/api/" + Account.namePlural() + "/:id").handler(accountHandler::update);
        router.delete("/api/" + Account.namePlural() + "/:id").handler(accountHandler::delete);

        TransactionHandler transactionHandler = new TransactionHandler();
        router.route("/api/" + Transaction.namePlural()).handler(BodyHandler.create());
        router.get("/api/" + Transaction.namePlural()).handler(transactionHandler::readList);
        router.get("/api/" + Transaction.namePlural() + "/:id").handler(transactionHandler::readId);
        router.post("/api/" + Transaction.namePlural()).handler(transactionHandler::create);
        router.put("/api/" + Transaction.namePlural() + "/:id").handler(transactionHandler::update);
        router.delete("/api/" + Transaction.namePlural() + "/:id").handler(transactionHandler::delete);

        router.route("/api/*").failureHandler(handler -> {
            final Throwable failure = handler.failure();
            if (failure == null) {
                handler.response().setStatusCode(Status.internalServerError).setStatusMessage("Internal Server Error").end();
            }

            logger.error(failure.getMessage());

            if (failure instanceof ApplicationException) {
                ApplicationException ae = (ApplicationException) failure;
                handler.response().setStatusCode(ae.getStatus()).putHeader("content-type", MimeType.application_text.toString()).end(ae.getMessage());
            } else if (failure instanceof DecodeException) {
                handler.response().setStatusCode(400).putHeader("content-type", MimeType.application_text.toString())
                       .end("Wrong format: " + failure.getMessage());
            } else {
                handler.response().setStatusCode(500).end("Unknown Server Error");
            }
        });

        server.requestHandler(router::accept);
        server.listen(config().getInteger("http.port", port));
    }
}
