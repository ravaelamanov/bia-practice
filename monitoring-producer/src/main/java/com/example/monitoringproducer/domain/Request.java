package com.example.monitoringproducer.domain;

import com.example.monitoringproducer.services.resolvers.util.AuthType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Request {
    public final static String REQUEST_URI_HEADER = "x-request-uri";

    private String uri;
    private String clientIpAddress;
    private Map<String, String> headers;
    private String query;
    private String userId;
    private AuthType authType;

    public String getHeader(String header) {
        if (headers == null) return null;

        return headers.get(header);
    }
}
