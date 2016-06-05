package me.dchuiko.spa.rest.http;

import io.vertx.core.http.HttpServerRequest;
import me.dchuiko.spa.rest.JsonType;

public class WebContext {
    public static final String context = "api";

    private final HttpServerRequest request;

    public static String uri(JsonType type) {
        return new UriParts("/", context, type.namePlural()).get();
    }

    public WebContext(HttpServerRequest request) {
        this.request = request;
    }

    public String baseUrl() {
        String absoluteUri = request.absoluteURI();
        String path = request.path();

        int startPath = absoluteUri.indexOf(path);
        return new UriParts(absoluteUri.substring(0, startPath), context).get();
    }

    public String absoluteURI() {
        return request.absoluteURI();
    }

    public String path() {
        return request.path();
    }

}
