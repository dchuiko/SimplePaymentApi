package me.dchuiko.spa.json.http;

public class MimeType {
    public static final MimeType application_json = new MimeType("application/json");

    private final String value;

    private MimeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
