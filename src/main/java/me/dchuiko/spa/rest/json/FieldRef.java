package me.dchuiko.spa.rest.json;

import me.dchuiko.spa.rest.JsonType;
import me.dchuiko.spa.rest.http.WebContext;

import java.util.UUID;

public class FieldRef extends HateosObject {
    public FieldRef(JsonType type, Href currentHref, String fieldName) {
        super(type, new Href(currentHref.getHref(), fieldName));
    }
}
