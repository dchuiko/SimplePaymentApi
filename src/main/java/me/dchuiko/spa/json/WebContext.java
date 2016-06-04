package me.dchuiko.spa.json;

public class WebContext {
    private static final String baseUrlTemplate = "%s://%s:%s";
    private final String baseUrl;



    public WebContext(String protocol, String host, int port) {
        this.baseUrl = String.format(baseUrlTemplate, protocol, host, port);
    }

    public String composeHref(String... params) {
        return concat(baseUrl, (Object[]) params);
    }

    public static String concat(Object uri, Object... parts) {
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
        if (endsWithDelimiter) {
            uri.append(part);
        } else {
            uri.append("/").append(part);
        }
    }
}
