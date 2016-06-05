package me.dchuiko.spa;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import me.dchuiko.spa.persistence.Accounts;
import me.dchuiko.spa.persistence.IdGenerator;
import me.dchuiko.spa.persistence.Transactions;
import me.dchuiko.spa.persistence.Users;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.handler.AccountHandler;
import me.dchuiko.spa.rest.handler.ContentTypeHandler;
import me.dchuiko.spa.rest.handler.FailureHandler;
import me.dchuiko.spa.rest.handler.TransactionHandler;
import me.dchuiko.spa.rest.handler.UserHandler;
import me.dchuiko.spa.rest.http.UriParts;
import me.dchuiko.spa.rest.http.WebContext;

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

        router.route().handler(new ContentTypeHandler());

        router.route(new UriParts("/", WebContext.context, "*").get()).failureHandler(new FailureHandler());

        Users users = new Users(idGenerator);
        Accounts accounts = new Accounts(idGenerator);
        Transactions transactions = new Transactions(idGenerator);

        UserHandler userHandler = new UserHandler(users, accounts, transactions);
        router.route(uri(User)).handler(BodyHandler.create());
        router.get(uri(User)).handler(userHandler::readList);
        router.get(uri(User) + "/:id").handler(userHandler::readId);
        router.get(uri(User) + "/:id/transactions").handler(userHandler::readTransactions);
        router.get(uri(User) + "/:id/accounts").handler(userHandler::readAccounts);
        router.post(uri(User)).handler(userHandler::create);
        router.put(uri(User) + "/:id").handler(userHandler::update);
        router.delete(uri(User) + "/:id").handler(userHandler::delete);

        AccountHandler accountHandler = new AccountHandler(accounts);
        router.route(uri(Account)).handler(BodyHandler.create());
        router.get(uri(Account)).handler(accountHandler::readList);
        router.get(uri(Account) + "/:id").handler(accountHandler::readId);
        router.post(uri(Account)).handler(accountHandler::create);
        router.put(uri(Account) + "/:id").handler(accountHandler::update);
        router.delete(uri(Account) + "/:id").handler(accountHandler::delete);

        TransactionHandler transactionHandler = new TransactionHandler(transactions);
        router.route(uri(Transaction)).handler(BodyHandler.create());
        router.get(uri(Transaction)).handler(transactionHandler::readList);
        router.get(uri(Transaction) + "/:id").handler(transactionHandler::readId);
        router.post(uri(Transaction)).handler(transactionHandler::create);
        router.put(uri(Transaction) + "/:id").handler(transactionHandler::update);
        router.delete(uri(Transaction) + "/:id").handler(transactionHandler::delete);

        server.requestHandler(router::accept);
        server.listen(config().getInteger("http.port", port), result -> {
            if (result.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(result.cause());
            }
        });
    }

    private String uri(JsonType type) {
        return new UriParts("/", WebContext.context, type.namePlural()).get();
    }

}
