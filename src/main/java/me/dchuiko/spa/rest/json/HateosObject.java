package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.dchuiko.spa.model.Identifiable;
import me.dchuiko.spa.rest.JsonType;

import static java.util.Objects.requireNonNull;

public abstract class HateosObject {
    private final Href href;
    private final JsonType type;

    protected HateosObject(JsonType type, Identifiable identifiable, String absoluteUri) {
        this(type, new Href(absoluteUri, identifiable.id().toString()));
    }

    protected HateosObject(JsonType type, Href href) {
        this.href = requireNonNull(href);
        this.type = requireNonNull(type);
    }

    @JsonProperty
    public Href getHref() {
        return href;
    }

    @JsonIgnore
    public JsonType getType() {
        return type;
    }

    @JsonProperty("type")
    public String getTypeAsString() {
        return type.name();
    }
}
