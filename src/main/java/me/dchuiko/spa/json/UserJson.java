package me.dchuiko.spa.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserJson extends HateosObject {
    private final UUID id;
    private final String name;

    @JsonCreator
    protected UserJson(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
