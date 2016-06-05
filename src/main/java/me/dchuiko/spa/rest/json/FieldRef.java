package me.dchuiko.spa.rest.json;

import me.dchuiko.spa.rest.JsonType;

public class FieldRef extends HateosObject {
    public FieldRef(JsonType type, Href currentHref, String fieldName) {
        super(type, new Href(currentHref.getHref(), fieldName));
    }
}
