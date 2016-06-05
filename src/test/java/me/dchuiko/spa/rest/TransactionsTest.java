package me.dchuiko.spa.rest;

import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import me.dchuiko.spa.model.AccountWithBalance;
import me.dchuiko.spa.model.Transaction;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.persistence.Accounts;
import me.dchuiko.spa.persistence.DaoFactory;
import me.dchuiko.spa.persistence.Transactions;
import me.dchuiko.spa.persistence.Users;
import me.dchuiko.spa.rest.http.Status;
import me.dchuiko.spa.rest.json.AccountJson;
import me.dchuiko.spa.rest.json.Ref;
import me.dchuiko.spa.rest.json.TransactionJson;
import me.dchuiko.spa.rest.json.UserJson;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(VertxUnitRunner.class)
public class TransactionsTest extends BaseRestTest {
    private Users users = DaoFactory.users;
    private Accounts accounts = DaoFactory.accounts;
    private Transactions transactions = DaoFactory.transactions;

    @Test
    public void shouldCreateTransactions(TestContext context) {
        final int initialBalance = 1000;

        final Async userAsync1 = context.async();
        UserJson sender = new UserJson("", "sender");
        post(JsonType.User, Json.encode(sender), response -> {
            context.assertEquals(Status.created, response.statusCode());
            userAsync1.complete();
        });
        userAsync1.awaitSuccess(1000);

        final Async userAsync2 = context.async();
        UserJson receiver = new UserJson("", "receiver");
        post(JsonType.User, Json.encode(receiver), response -> {
            context.assertEquals(Status.created, response.statusCode());
            userAsync2.complete();
        });
        userAsync2.awaitSuccess(1000);

        final List<User> users = this.users.list();
        assertEquals(2, users.size());


        final Async accountAsync = context.async();
        final Ref senderRef = new Ref(JsonType.User.name(), users.get(0).id());
        AccountJson senderAccount = new AccountJson("", "123", "Main", initialBalance, senderRef);
        post(JsonType.Account, Json.encode(senderAccount), response -> {
            context.assertEquals(Status.created, response.statusCode());
            accountAsync.complete();
        });

        final Ref receiverRef = new Ref(JsonType.User.name(), users.get(1).id());
        AccountJson receiverAccount = new AccountJson("", "321", "Main", initialBalance, receiverRef);
        post(JsonType.Account, Json.encode(receiverAccount), response -> {
            context.assertEquals(Status.created, response.statusCode());
            accountAsync.complete();
        });

        accountAsync.awaitSuccess(1000);
        final List<AccountWithBalance> accounts = this.accounts.list();
        assertEquals(2, accounts.size());

        final Ref senderAccountRef = new Ref(JsonType.Account.name(), accounts.get(0).id());
        final Ref receiverAccountRef = new Ref(JsonType.Account.name(), accounts.get(1).id());

        final Async t1Async = context.async();
        TransactionJson t1 = new TransactionJson("", LocalDateTime.now(), senderAccountRef, receiverAccountRef, 100);
        post(JsonType.Transaction, Json.encode(t1), response -> {
            context.assertEquals(Status.created, response.statusCode());

            assertBody(response, s -> {
                TransactionJson t1Json = Json.decodeValue(s, TransactionJson.class);
                context.assertEquals(100, t1Json.getAmount());
                t1Async.complete();
            });
        });
        t1Async.awaitSuccess(1000);

        final List<Transaction> transactions = this.transactions.list();
        assertEquals(1, transactions.size());

        final Async accountsAsync1 = context.async();
        get(JsonType.Account, senderAccountRef.getId(), response -> {
            context.assertEquals(Status.ok, response.statusCode());
            assertBody(response, s -> {
                AccountJson sa1 = Json.decodeValue(s, AccountJson.class);
                context.assertEquals(initialBalance - 100, sa1.getBalance());
                accountsAsync1.complete();
            });
        });
        accountsAsync1.awaitSuccess(1000);

        final Async accountsAsync2 = context.async();
        get(JsonType.Account, receiverAccountRef.getId(), response -> {
            context.assertEquals(Status.ok, response.statusCode());
            assertBody(response, s -> {
                AccountJson sa2 = Json.decodeValue(s, AccountJson.class);
                context.assertEquals(initialBalance + 100, sa2.getBalance());
                accountsAsync2.complete();
            });
        });
        accountsAsync2.awaitSuccess(1000);
    }
}
