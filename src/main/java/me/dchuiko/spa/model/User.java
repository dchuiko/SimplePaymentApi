package me.dchuiko.spa.model;

import java.util.UUID;

public class User implements Identifiable, Copiable<User> {
    private final UUID id;
    private final String name;

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
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
        return new User(id, name);
    }
}
