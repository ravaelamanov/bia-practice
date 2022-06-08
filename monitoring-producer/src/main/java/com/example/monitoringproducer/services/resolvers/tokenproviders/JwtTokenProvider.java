package com.example.monitoringproducer.services.resolvers.tokenproviders;

import com.example.monitoringproducer.domain.Request;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JwtTokenProvider implements TokenProvider{
    private final static String AUTHORIZATION = "authorization";

    @Override
    public String provide(Request request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Objects.requireNonNull(authorizationHeader);

        String[] parts = authorizationHeader.split(" ");

        if (parts.length < 2 || !parts[0].equals("Bearer") || parts[1].isBlank()) {
            throw new IllegalArgumentException("Invalid Authorization header value! Value: " + authorizationHeader);
        }

        return parts[1];
    }
}
