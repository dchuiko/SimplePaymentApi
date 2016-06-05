package me.dchuiko.spa.model;

import me.dchuiko.spa.BaseTest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AccountWithBalanceTest extends BaseTest {
    private UUID senderId = UUID.randomUUID();
    private UUID receiverId = UUID.randomUUID();

    private UUID senderAccountId = UUID.randomUUID();
    private UUID receiverAccountId = UUID.randomUUID();

    @Test
    public void shouldHaveCorrectBalance() {
        int initialBalance = 1000;

        List<Transaction> accountTransactions =
                Arrays.asList(send(300), send(100), recieve(200), sendSelf(400), recieve(200), send(50), recieve(10), recieve(10), recieve(30));

        AccountWithBalance a = new AccountWithBalance(new Account(senderAccountId, UUID.randomUUID(), "123", initialBalance, ""), accountTransactions);
        assertEquals(1000, a.balance());

        a = new AccountWithBalance(new Account(receiverAccountId, UUID.randomUUID(), "123", 0, ""), accountTransactions);
        assertEquals(0, a.balance());
    }

    private Transaction send(int amount) {
        return new Transaction(UUID.randomUUID(), LocalDateTime.now(), senderId, senderAccountId, receiverId, receiverAccountId, amount);
    }

    private Transaction recieve(int amount) {
        return new Transaction(UUID.randomUUID(), LocalDateTime.now(), receiverId, receiverAccountId, senderId, senderAccountId, amount);
    }

    private Transaction sendSelf(int amount) {
        return new Transaction(UUID.randomUUID(), LocalDateTime.now(), senderId, senderAccountId, senderId, senderAccountId, amount);
    }
}
