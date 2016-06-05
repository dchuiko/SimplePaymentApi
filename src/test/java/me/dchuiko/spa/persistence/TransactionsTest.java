package me.dchuiko.spa.persistence;

import me.dchuiko.spa.BaseTest;
import me.dchuiko.spa.model.Account;
import me.dchuiko.spa.model.User;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.exception.ApplicationException;
import me.dchuiko.spa.rest.json.AccountJson;
import me.dchuiko.spa.rest.json.Ref;
import me.dchuiko.spa.rest.json.TransactionJson;
import me.dchuiko.spa.rest.json.UserJson;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static java.lang.Math.abs;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TransactionsTest extends BaseTest {
    private Accounts accounts = DaoFactory.accounts;
    private Users users = DaoFactory.users;

    private final int initialBalance = 1000;

    private Transactions transactions = DaoFactory.transactions;
    private Ref senderRef;
    private Ref senderAccountRef;
    private Ref receiverRef;
    private Ref receiverAccountRef;

    @Before
    public void before() {
        User sender = users.create(new UserJson("", "sender"));
        UUID senderId = sender.id();
        senderRef = new Ref(JsonType.User.name(), senderId);

        Account senderAccount = accounts.create(new AccountJson("", "123", "", initialBalance, senderRef));
        UUID senderAccountId = senderAccount.id();
        senderAccountRef = new Ref(JsonType.Account.name(), senderAccountId);

        User receiver = users.create(new UserJson("", "receiver"));
        UUID receiverId = receiver.id();
        receiverRef = new Ref(JsonType.User.name(), receiverId);

        Account receiverAccount = accounts.create(new AccountJson("", "321", "", initialBalance, receiverRef));
        UUID receiverAccountId = receiverAccount.id();
        receiverAccountRef = new Ref(JsonType.Account.name(), receiverAccountId);
    }

    @Test
    public void shouldHaveCorrectBalance() {
        transactions.create(send(100));
        transactions.create(receive(50));
        assertEquals(initialBalance - 100 + 50, accounts.id(senderAccountRef.getId()).balance());
        assertEquals(initialBalance + 100 - 50, accounts.id(receiverAccountRef.getId()).balance());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowInsufficientFunds() {
        transactions.create(send(2000));
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowLessThenZeroAmount() {
        transactions.create(send(-200));
    }

    @Test
    public void shouldCorrectlyWorkConcurrently() {
        CountDownLatch latch = new CountDownLatch(1);

        final int iterationNumber = 5000;
        List<Thread> threadList =
                Arrays.asList(new TestThread(iterationNumber, latch),
                              new TestThread(iterationNumber, latch),
                              new TestThread(iterationNumber, latch),
                              new TestThread(iterationNumber, latch));

        threadList.forEach(Thread::start);
        latch.countDown();
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        final int senderBalance = accounts.id(senderAccountRef.getId()).balance();
        assertTrue(senderBalance > 0);
        final int receiverBalance = accounts.id(receiverAccountRef.getId()).balance();
        assertTrue(receiverBalance > 0);
        assertEquals(initialBalance * 2, senderBalance + receiverBalance);
    }

    private TransactionJson send(int amount) {
        return new TransactionJson("", LocalDateTime.now(), senderAccountRef, receiverAccountRef, amount);
    }

    private TransactionJson receive(int amount) {
        return new TransactionJson("", LocalDateTime.now(), receiverAccountRef, senderAccountRef, amount);
    }

    private class TestThread extends Thread {
        private final int iterationNumber;
        private final CountDownLatch latch;

        private TestThread(int iterationNumber, CountDownLatch latch) {
            this.iterationNumber = iterationNumber;
            this.latch = latch;
        }

        private int generateRandom() {
            double signValue = Math.random() * 2;
            int sign = signValue < 1.0 ? -1 : 1;

            return sign * new Double(initialBalance * Math.random()).intValue();
        }

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < iterationNumber; i++) {
                try {
                    int random = generateRandom();

                    TransactionJson t;
                    if (random < 0) {
                        t = send(abs(random));
                    } else {
                        t = receive(abs(random));
                    }
                    transactions.create(t);

                } catch (Exception e) {
                    // may encounter
                }
            }
        }
    }
}
