package com.example.codeassessmentexercise.connector;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

public class HttpConnector {
    private final WebClient webClient;

    public HttpConnector(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getRequest(String endpoint) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
