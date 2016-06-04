package me.dchuiko.spa.persistence;

import java.util.UUID;

public interface IdGenerator {
    UUID id();

    static IdGenerator generator() {
        return UUID::randomUUID;
    }
}
