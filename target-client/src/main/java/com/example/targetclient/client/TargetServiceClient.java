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
//        headers.add("Authorization", "Bearer .eyJzdWIiOiJBbHZlbnRhIiwiYXVkIjoiRDB6b243SjF0WXVnRE13QTZRQVdRMjhpazk0YSIsIm5iZiI6MTY1NDU5MDkwNCwicm9sZSI6IlRyYW5zcG9ydENvbXBhbnlBZG1pbiIsImNvbXBhbnlfaWQiOiI1NTNjMjM1ZS03OTlkLTQ4YTQtYTcwYi0zOWFmZjViYTZmMGEiLCJ1c2VyX2lkIjoiNGIwNzU5YjgtNjNlMC00ZjdlLWE0NzgtZmEyNTU3YjYxZDMxIiwiYXpwIjoiRDB6b243SjF0WXVnRE13QTZRQVdRMjhpazk0YSIsInNjb3BlIjoidHJhZmZpY19hdXRoIiwiaXNzIjoiaHR0cHM6XC9cL3dzbzIuZGV2MS50cmFmZmljLm9ubGluZVwvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1NDU5NDUwNCwiaWF0IjoxNjU0NTkwOTA0LCJqdGkiOiIzNmNmNDc1OC1hYjc3LTQzYmMtYjhjOS0zZGJkMzU0ZDdlYTEifQ.");
        headers.add("Authorization", "Bearer MDAyOWxvY2F0aW9uIHN5bmFwc2UuZGV2MS50cmFmZmljLm9ubGluZQowMDEzaWRlbnRpZmllciBrZXkKMDAxMGNpZCBnZW4gPSAxCjAwMzdjaWQgdXNlcl9pZCA9IEBkZWxrb2d2OnN5bmFwc2UuZGV2MS50cmFmZmljLm9ubGluZQowMDE2Y2lkIHR5cGUgPSBhY2Nlc3MKMDAyMWNpZCBub25jZSA9ID1BNjlFUU9rQk8zYWU9NywKMDAyZnNpZ25hdHVyZSApXA5XL25t55GHeVsJPn1keQo");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate
                .exchange(BASE_URL + "/qwer/abc", HttpMethod.GET, httpEntity, String.class);
        logResponse("GET", response);
    }

    public void doPostAndLog() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer .eyJzdWIiOiJBbHZlbnRhIiwiYXVkIjoiRDB6b243SjF0WXVnRE13QTZRQVdRMjhpazk0YSIsIm5iZiI6MTY1NDU5MDkwNCwicm9sZSI6IlRyYW5zcG9ydENvbXBhbnlBZG1pbiIsImNvbXBhbnlfaWQiOiI1NTNjMjM1ZS03OTlkLTQ4YTQtYTcwYi0zOWFmZjViYTZmMGEiLCJ1c2VyX2lkIjoiNGIwNzU5YjgtNjNlMC00ZjdlLWE0NzgtZmEyNTU3YjYxZDMxIiwiYXpwIjoiRDB6b243SjF0WXVnRE13QTZRQVdRMjhpazk0YSIsInNjb3BlIjoidHJhZmZpY19hdXRoIiwiaXNzIjoiaHR0cHM6XC9cL3dzbzIuZGV2MS50cmFmZmljLm9ubGluZVwvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1NDU5NDUwNCwiaWF0IjoxNjU0NTkwOTA0LCJqdGkiOiIzNmNmNDc1OC1hYjc3LTQzYmMtYjhjOS0zZGJkMzU0ZDdlYTEifQ.");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "?access_token=MDAyOWxvY2F0aW9uIHN5bmFwc2UuZGV2MS50cmFmZmljLm9ubGluZQowMDEzaWRlbnRpZmllciBrZXkKMDAxMGNpZCBnZW4gPSAxCjAwMzdjaWQgdXNlcl9pZCA9IEBkZWxrb2d2OnN5bmFwc2UuZGV2MS50cmFmZmljLm9ubGluZQowMDE2Y2lkIHR5cGUgPSBhY2Nlc3MKMDAyMWNpZCBub25jZSA9ID1BNjlFUU9rQk8zYWU9NywKMDAyZnNpZ25hdHVyZSApXA5XL25t55GHeVsJPn1keQo", httpEntity, String.class);
        logResponse("POST", response);
    }

    private void logResponse(String method, ResponseEntity<?> response) {
        log.info(String.format("%S response: %s", method, response.toString()));
    }
}
