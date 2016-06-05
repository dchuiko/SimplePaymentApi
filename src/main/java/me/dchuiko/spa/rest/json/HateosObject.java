package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.dchuiko.spa.model.Identifiable;
import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public abstract class HateosObject {
    private final Href href;
    private final JsonType type;

    protected HateosObject(WebContext webContext, JsonType type, UUID id) {
        this(type, createHref(webContext, type, id));
    }

    protected HateosObject(JsonType type, Href href) {
        this.href = requireNonNull(href);
        this.type = requireNonNull(type);
    }

    protected static Href createHref(WebContext webContext, JsonType type, UUID id) {
        return new Href(webContext.baseUrl(), type.namePlural(), id.toString());
    }

    protected static Href createRef(WebContext webContext, JsonType type, UUID id, String ref) {
        return new Href(webContext.baseUrl(), type.namePlural(), id.toString(), ref);
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
