package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

import java.util.UUID;

public class Ref extends HateosObject {
    private final UUID id;

    public Ref(WebContext webContext, JsonType type, UUID id) {
        super(webContext, type, id);
        this.id = id;
    }

    @JsonCreator
    public Ref(@JsonProperty("type") String type, @JsonProperty("id") UUID id) {
        super(JsonType.find(type).get(), new Href());
        this.id = id;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }
}
