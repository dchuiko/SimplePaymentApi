package me.dchuiko.spa.model;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Account implements Identifiable, Copiable<Account> {
    private final UUID id;
    private final UUID userId;
    private final String number;
    private final String alias;

    public Account(UUID id, UUID userId, String number) {
        this(id, userId, number, null);
    }

    public Account(UUID id, UUID userId, String number, String alias) {
        this.id = requireNonNull(id);
        this.userId = requireNonNull(userId);
        this.number = requireNonNull(number);
        this.alias = alias;
    }


    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Account copy() {
        return new Account(id, userId,  number, alias);
    }
}
