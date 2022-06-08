package com.example.targetclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component

public class TargetServiceClient {
    private final Logger log = LoggerFactory.getLogger(TargetServiceClient.class);
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost";

    @Autowired
    public TargetServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void doGetAndLog() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-type", "jwt");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate
                .exchange(BASE_URL + "/qwer", HttpMethod.GET, httpEntity, String.class);
        logResponse("GET", response);
    }

    public void doPostAndLog() {
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, null, String.class);
        logResponse("POST", response);
    }

    private void logResponse(String method, ResponseEntity<?> response) {
        log.info(String.format("%S response: %s", method, response.toString()));
    }
}
