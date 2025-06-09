package com.playfolio.app.controllers;

import java.awt.PageAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

@RestController
public class ProxyController {
    @Autowired
    TokenController tokenController;

    @Value("${twitch.client-id}")
    private String clientId;

    @PostMapping("/api/igdb/proxy")
    public ResponseEntity<?> proxyIGDB(@RequestBody String body, @RequestHeader("igdb-endpoint") String igdbEndpoint) {
        String accessToken = tokenController.getTwitchAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        String url = "https://api.igdb.com" + igdbEndpoint;

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
