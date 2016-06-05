package me.dchuiko.spa.model;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Account implements Identifiable, Copiable<Account> {
    private final UUID id;
    private final UUID userId;
    private final String number;
    private final int initialBalance;
    private final String alias;

    public Account(UUID id, UUID userId, String number, int initialBalance) {
        this(id, userId, number, initialBalance, "");
    }

    public Account(Account account) {
        this(account.id, account.userId, account.number, account.initialBalance, account.alias);
    }

    public Account(UUID id, UUID userId, String number, int initialBalance, String alias) {
        this.id = requireNonNull(id);
        this.userId = requireNonNull(userId);
        this.number = requireNonNull(number);
        this.initialBalance = initialBalance;
        this.alias = alias;
    }

    @Override
    public UUID id() {
        return id;
    }

    public String alias() {
        return alias;
    }

    public String number() {
        return number;
    }

    public int initialBalance() {
        return initialBalance;
    }

    public UUID userId() {
        return userId;
    }

    @Override
    public Account copy() {
        return new Account(this);
    }
}
