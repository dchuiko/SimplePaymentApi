package me.dchuiko.spa.rest.http;

import io.vertx.core.http.HttpServerRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class WebContextTest {
    HttpServerRequest request;

    @Before
    public void before() {
        request = Mockito.mock(HttpServerRequest.class);
    }

    @Test
    public void shouldReturnCorrectBaseUrl() {
        when(request.absoluteURI()).thenReturn("http://localhost:8081/api/users/");
        when(request.path()).thenReturn("/api/users/");

        assertEquals("http://localhost:8081/api", new WebContext(request).baseUrl());
    }
}
