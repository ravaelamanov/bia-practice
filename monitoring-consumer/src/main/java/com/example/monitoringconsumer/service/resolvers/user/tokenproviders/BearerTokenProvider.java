package com.example.monitoringconsumer.service.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.stereotype.Component;

@Component
public class BearerTokenProvider implements TokenProvider{
    @Override
    public String provide(Request request) {
        String authorizationHeader = TokenProviderUtil.getAuthorizationHeader(request)
                .orElseThrow(() -> new IllegalArgumentException("No Authorization header in request: %s".formatted(request.toString())));

        String[] parts = authorizationHeader.split(" ");

        if (parts.length < 2 || !parts[0].equals("Bearer") || parts[1].isBlank()) {
            throw new IllegalArgumentException("Invalid Authorization header value! Value: " + authorizationHeader);
        }

        return parts[1];
    }
}
