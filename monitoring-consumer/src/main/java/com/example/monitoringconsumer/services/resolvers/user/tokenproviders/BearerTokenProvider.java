package com.example.monitoringconsumer.services.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.messages.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class BearerTokenProvider implements TokenProvider{
    @Override
    public String provide(Request request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No Authorization header in request: %s", request)));

        String[] parts = authorizationHeader.split(" ");

        if (parts.length < 2 || !parts[0].equals("Bearer") || parts[1].isBlank()) {
            throw new IllegalArgumentException("Invalid Authorization header value! Value: " + authorizationHeader);
        }

        return parts[1];
    }
}
