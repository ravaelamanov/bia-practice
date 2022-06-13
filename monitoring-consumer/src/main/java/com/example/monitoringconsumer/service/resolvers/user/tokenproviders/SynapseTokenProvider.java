package com.example.monitoringconsumer.service.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SynapseTokenProvider implements TokenProvider {
    private final BearerTokenProvider bearerTokenProvider;
    private final static String QUERY_PARAM = "access_token";

    @Autowired
    public SynapseTokenProvider(BearerTokenProvider bearerTokenProvider) {
        this.bearerTokenProvider = bearerTokenProvider;
    }

    @Override
    public String provide(Request request) {
        String token;
        try {
            token = bearerTokenProvider.provide(request);
        } catch (Exception exception) {
            token = request.getQuery(QUERY_PARAM)
                    .orElseThrow(() -> new IllegalArgumentException("No %s query parameter in request: %s".formatted(QUERY_PARAM, request.toString())));
            TokenProviderUtil.requireNonBlank(token, new IllegalArgumentException("Token in query parameters is blank!"));
        }
        return token;
    }
}
