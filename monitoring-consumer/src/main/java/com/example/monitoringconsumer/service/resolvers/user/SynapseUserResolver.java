package com.example.monitoringconsumer.service.resolvers.user;

import com.example.monitoringconsumer.domain.Request;
import com.example.monitoringconsumer.service.resolvers.user.tokenproviders.SynapseTokenProvider;
import com.example.monitoringconsumer.service.resolvers.user.tokenproviders.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class SynapseUserResolver implements UserResolver {
    private final TokenProvider provider;
    private final static String USER_ID_CLAIM = "cid user_id";

    @Autowired
    public SynapseUserResolver(SynapseTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void resolve(Request request) {
        String token = provider.provide(request);
        String userId = decodeBase64(token).lines()
                .filter(this::hasUserIdClaim)
                .map(this::extractUserIdFromTokenLine)
                .filter(id -> !id.isBlank())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No user id claim in this token!"));
        request.setUserId(userId);
    }

    private String decodeBase64(String str) {
        return new String(Base64.getUrlDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
    }

    private String extractUserIdFromTokenLine(String tokenLine) {
        String[] parts = tokenLine.split("=", 2);
        if (parts.length < 2)
            throw new IllegalStateException("Unsupported claim format! At least one '=' expected. Claim line is: " + tokenLine);
        return parts[1].trim();
    }

    private boolean hasUserIdClaim(String tokenLine) {
        return tokenLine.contains(USER_ID_CLAIM);
    }

    @Override
    public AuthType authType() {
        return AuthType.SYNAPSE;
    }
}
