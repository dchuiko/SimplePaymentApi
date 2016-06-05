package me.dchuiko.spa.persistence;

import java.util.UUID;

import static com.fasterxml.uuid.Generators.timeBasedGenerator;

public interface IdGenerator {
    UUID id();

    static IdGenerator generator() {
        return () -> timeBasedGenerator().generate();
    }
}
