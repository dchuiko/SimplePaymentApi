package me.dchuiko.spa.persistence;

import java.util.UUID;

public interface IdGenerator {
    UUID id();

    IdGenerator Default = new IdGenerator() {
        @Override
        public UUID id() {
            return UUID.randomUUID();
        }
    };
}
