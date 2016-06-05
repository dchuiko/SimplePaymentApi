package me.dchuiko.spa.rest.json;

import com.fasterxml.jackson.annotation.JsonValue;
import me.dchuiko.spa.rest.http.UriParts;

public class Href {
    private final String href;

    public Href(String... path) {
        this(new UriParts("", (Object[]) path).get());
    }

    public Href(Href base, String... path) {
        this(new UriParts(base.getHref(), (Object[]) path).get());
    }

    public Href(String href) {
        this.href = href;
    }

    @JsonValue
    public String getHref() {
        return href;
    }


}
