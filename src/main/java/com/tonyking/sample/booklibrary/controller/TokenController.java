package com.tonyking.sample.booklibrary.controller;

import com.tonyking.sample.booklibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/oauth")
public class TokenController {

    public static final String AUTHORIZATION_CODE = "authorization_code";

    @Value("${bookapp.oauth.client.auth.basic}")
    private String clientAuthString;

    @Value("${bookapp.oauth.token.url}")
    private String tokenUrl;

    @Autowired
    private BookService bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam("code") String code, @RequestParam("redirectUrl") String redirectUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", clientAuthString);

        // Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AUTHORIZATION_CODE);
        body.add("code", code);
        body.add("redirect_uri", redirectUrl);

        // HttpEntity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

        return response;
    }

}
