package me.dchuiko.spa.model;

import java.util.ArrayList;
import java.util.List;

public class AccountWithBalance extends Account {
    private final List<Transaction> accountTransactions = new ArrayList<>();

    public AccountWithBalance(Account account, List<Transaction> accountTransactions) {
        super(account);
        this.accountTransactions.addAll(accountTransactions);
    }

    public int balance() {
        return initialBalance() + accountTransactions.stream().filter(t -> t.receiverAccountId().equals(id()) || t.senderAccountId().equals(id())).mapToInt(t -> {
            int amount = t.amount();
            if (t.senderAccountId().equals(id()) && t.receiverAccountId().equals(id())) {
                amount = 0;
            }
            if (t.senderAccountId().equals(id())) {
                amount *= -1;
            }
            return amount;
        }).sum();
    }
}
