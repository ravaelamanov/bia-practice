package com.example.monitoringproducer.domain;

import com.example.monitoringproducer.util.AuthType;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.time.Instant;

@Data
@Builder
public class Request {
    private String uri;
    private String clientIpAddress;
    private MultiValueMap<String, String> headers;
    private MultiValueMap<String, String> query;
    private AuthType authType;
    private String targetName;
    private Instant timestamp;
}
