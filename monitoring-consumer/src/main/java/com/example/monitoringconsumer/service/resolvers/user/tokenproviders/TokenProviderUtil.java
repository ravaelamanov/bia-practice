package com.example.monitoringconsumer.service.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

public class TokenProviderUtil {
    private TokenProviderUtil() {
        throw new AssertionError();
    }
    public static Optional<String> getAuthorizationHeader(Request request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());
    }

    public static void requireNonBlank(String str, RuntimeException runtimeException) {
        if (str.isBlank()) {
            throw runtimeException;
        }
    }
}
