package me.dchuiko.spa.rest.http;

import me.dchuiko.spa.rest.JsonType;

public class BaseUrl {
    private static final String basePath = "api";
    public final JsonType type;

    public BaseUrl(JsonType type) {
        this.type = type;
    }

    public String typePath(JsonType type) {
        return "api/" + type.namePlural();
    }
}
