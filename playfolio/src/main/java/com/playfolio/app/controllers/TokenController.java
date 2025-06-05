package com.playfolio.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/twitch")
public class TokenController {

    @Value("${twitch.client-id}")
    private String clientId;

    @Value("${twitch.client-secret}")
    private String clientSecret;

    private final String TOKEN_URL = "https://id.twitch.tv/oauth2/token";

    @PostMapping("/token")
    public ResponseEntity<?> getTwitchToken() {
        RestTemplate restTemplate = new RestTemplate();

        String url = TOKEN_URL +
                "?client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=client_credentials";

        ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);

        return ResponseEntity.ok(response.getBody());
    }
}