package com.tonyking.sample.booklibrary.controller;

import com.tonyking.sample.booklibrary.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TokenControllerTest {

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private BookService bookService;

    @Mock
    private RestTemplate restTemplate;

    private static final String CLIENT_AUTH_STRING = "basicAuthString";
    private static final String TOKEN_URL = "https://example.com/oauth/token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenController, "clientAuthString", CLIENT_AUTH_STRING);
        ReflectionTestUtils.setField(tokenController, "tokenUrl", TOKEN_URL);
    }

    @Test
    void testGetToken() {
        String code = "test_code";
        String redirectUrl = "https://example.com/redirect";
        String responseBody = "{\"access_token\":\"test_access_token\",\"token_type\":\"bearer\"}";

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(eq(TOKEN_URL), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<String> actualResponse = tokenController.getToken(code, redirectUrl);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        verify(restTemplate, times(1)).exchange(eq(TOKEN_URL), eq(HttpMethod.POST), any(), eq(String.class));
    }
}