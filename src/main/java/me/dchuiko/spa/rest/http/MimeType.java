package me.dchuiko.spa.rest.http;

public class MimeType {
    public static final MimeType application_json = new MimeType("application/json");
    public static final MimeType application_text = new MimeType("application/text");

    private final String value;

    private MimeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
