package com.example.monitoringproducer.controllers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.ProducerService;
import com.example.monitoringproducer.util.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestController
@Slf4j
public class ProducerController {
    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }


    @RequestMapping("/jwt/**")
    void publishJwt(HttpServletRequest httpServletRequest,
                    @RequestHeader("x-target-service") String targetName,
                    @RequestHeader MultiValueMap<String, String> headers,
                    @RequestParam(required = false) MultiValueMap<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.JWT);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    @RequestMapping("/synapse/**")
    void publishSynapse(HttpServletRequest httpServletRequest,
                        @RequestHeader("x-target-service") String targetName,
                        @RequestHeader MultiValueMap<String, String> headers,
                        @RequestParam(required = false) MultiValueMap<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.SYNAPSE);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    @RequestMapping("/**")
    void publishDefault(HttpServletRequest httpServletRequest,
                       @RequestHeader("x-target-service") String targetName,
                       @RequestHeader MultiValueMap<String, String> headers,
                       @RequestParam(required = false) MultiValueMap<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.DEFAULT);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    private Request makeRequest(String targetName,
                                MultiValueMap<String, String> headers,
                                MultiValueMap<String, String> query,
                                HttpServletRequest httpServletRequest,
                                AuthType authType) {
        return Request.builder()
                .targetName(targetName)
                .headers(headers)
                .query(query)
                .uri(getTargetUri(httpServletRequest.getRequestURI(), authType))
                .httpMethod(httpServletRequest.getMethod())
                .clientIpAddress(httpServletRequest.getRemoteAddr())
                .authType(authType)
                .timestamp(Instant.now())
                .build();
    }

    private String getTargetUri(String uri, AuthType authType) {
        String targetUri = "";
        if (authType.equals(AuthType.DEFAULT)) {
            targetUri = uri;
        }
        else {
            String[] parts = uri.split("/", 3); // example split uri: ["", {$auth_type}, {$request_uri}]
            if (parts.length > 2) {
                targetUri = "/" + parts[2];
            }
        }

        return targetUri;
    }

    private void publishRequest(Request request) {
        try {
            service.publish(request);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
        }
    }
}
