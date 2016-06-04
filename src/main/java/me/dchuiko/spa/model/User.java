package me.dchuiko.spa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User implements Identifiable, Copiable<User> {
    private final UUID id;
    private final String name;
    private final List<Account> accounts;

    public User(UUID id, String name) {
        this(id, name, Collections.emptyList());
    }

    private User(UUID id, String name, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = new ArrayList<>(accounts);
    }

    @Override
    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public User copy() {
        return new User(id, name, accounts);
    }
}
