package com.example.monitoringproducer.domain;

import com.example.monitoringproducer.util.AuthType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Request {
    private String uri;
    private String clientIpAddress;
    private Map<String, String> headers;
    private Map<String, String> query;
    private AuthType authType;
    private String targetName;
}
