package com.example.monitoringproducer.controllers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.ProducerService;
import com.example.monitoringproducer.util.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
                    @RequestHeader Map<String, String> headers, // switched to Map from MultiValueMap because consumer cannot deserialize it and fails with ClassCastException
                    @RequestParam(required = false) Map<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.JWT);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    @RequestMapping("/synapse/**")
    void publishSynapse(HttpServletRequest httpServletRequest,
                        @RequestHeader("x-target-service") String targetName,
                        @RequestHeader Map<String, String> headers,
                        @RequestParam(required = false) Map<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.SYNAPSE);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    @RequestMapping("/**")
    void publishDefault(HttpServletRequest httpServletRequest,
                       @RequestHeader("x-target-service") String targetName,
                       @RequestHeader Map<String, String> headers,
                       @RequestParam(required = false) Map<String, String> query) {
        Request request = makeRequest(targetName, headers, query, httpServletRequest, AuthType.DEFAULT);
        log.info("Handling mirrored request: " + request.toString());

        publishRequest(request);
    }

    private Request makeRequest(String targetName,
                                Map<String, String> headers,
                                Map<String, String> query,
                                HttpServletRequest httpServletRequest,
                                AuthType authType) {
        return Request.builder()
                .targetName(targetName)
                .headers(headers)
                .query(query)
                .uri(getTargetUri(httpServletRequest))
                .clientIpAddress(httpServletRequest.getRemoteAddr())
                .authType(authType)
                .build();
    }

    private String getTargetUri(HttpServletRequest request) {
        String[] parts = request.getRequestURI().split("/", 3); // example split uri: ["", {auth_type}, {$request_uri}]
        String targetUri = "";
        if (parts.length > 2) {
            targetUri = parts[2];
        }
        return "/" + targetUri;
    }

    private void publishRequest(Request request) {
        try {
            service.publish(request);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
        }
    }

    private void delay(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
