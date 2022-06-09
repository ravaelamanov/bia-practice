package com.example.monitoringconsumer.domain;

import com.example.monitoringconsumer.service.resolvers.user.AuthType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String uri;
    private String clientIpAddress;
    private Map<String, String> headers;
    private Map<String, String> query;
    private AuthType authType;
    private String targetName;
    private String userId;

    public String getHeader(String header) {
        if (headers == null) return null;
        return headers.get(header);
    }
}
