package me.dchuiko.spa.rest.http;

public class UriParts {
    private final Object uri;
    private final Object[] parts;

    public UriParts(Object uri, Object... parts) {
        this.uri = uri;
        this.parts = parts;
    }

    public String get() {
        return concat(uri, parts);
    }

    private static String concat(Object uri, Object... parts) {
        StringBuilder result = new StringBuilder(uri.toString());
        for (Object part : parts) {
            concatPart(result, part != null ? part.toString() : null);
        }
        return result.toString();
    }

    private static void concatPart(StringBuilder uri, String part) {
        if (part == null || part.isEmpty()) {
            return;
        }
        final boolean endsWithDelimiter = uri.length() > 0 && uri.charAt(uri.length() - 1) == '/';
        final boolean startsWithDelimiter = part.length() > 0 && part.charAt(0) == '/';
        if (startsWithDelimiter) {
            part = part.substring(1);
        }
        if (endsWithDelimiter || uri.length() == 0) {
            uri.append(part);
        } else {
            uri.append("/").append(part);
        }
    }
}
