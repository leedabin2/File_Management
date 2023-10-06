package com.example.file_management.google.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.json.JSONObject;
import com.example.file_management.google.model.dto.auth.AuthCodeDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GoogleOAuth2Service {

    private static final Logger logger = LoggerFactory.getLogger(GoogleOAuth2Service.class);

    @Value("${google.clientId}")
    private String clientId;
    @Value("${google.clientSecret}")
    private String clientSecret;
    @Value("${google.tokenUrl}")
    private String tokenUrl;
    WebClient webClient = WebClient.create();

    public String getAccessToken(AuthCodeDto authCodeDto, String redirectUri) {
        Mono<String> responseMono = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", authCodeDto.getAuthCode())
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code"))
                .retrieve()

                .onStatus(status -> status.value() >= 400, clientResponse ->
                        clientResponse.bodyToMono(String.class).map(errorMessage -> {
                            logger.error("Error occurred while calling Google OAuth2 API: " + errorMessage);
                            return new RuntimeException(errorMessage);
                        })
                )
                // successful responses (200-299)
                .bodyToMono(String.class);

        String responseString = responseMono.block();

        JSONObject jsonObject = new JSONObject(responseString);
        return jsonObject.getString("access_token");

    }
}
