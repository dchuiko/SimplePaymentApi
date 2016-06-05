package me.dchuiko.spa.rest.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UriPartsTest {
    @Test
    public void shouldConcatCorrectly() {
        assertEquals("/api/*", new UriParts("/", WebContext.context, "*").get());
        assertEquals("/api/users", new UriParts("/", WebContext.context, "users").get());
    }
}
