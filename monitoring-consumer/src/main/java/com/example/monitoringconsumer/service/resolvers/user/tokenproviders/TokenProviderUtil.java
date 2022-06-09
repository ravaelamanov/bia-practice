package com.example.monitoringconsumer.service.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.http.HttpHeaders;

public class TokenProviderUtil {
    private TokenProviderUtil() {
        throw new AssertionError();
    }
    public static String getAuthorizationHeader(Request request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());
    }

    public static void requireNonBlank(String str, RuntimeException runtimeException) {
        if (str.isBlank()) {
            throw runtimeException;
        }
    }
}
