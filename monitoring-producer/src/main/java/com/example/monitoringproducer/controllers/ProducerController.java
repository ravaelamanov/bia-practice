package com.example.monitoringproducer.controllers;

import com.example.monitoringproducer.config.infrastructure.CurrentRequest;
import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ProducerController {
    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }


    @RequestMapping("/**")
    void handle(@CurrentRequest Request request) {
        log.info("Handling mirrored request: " + request.toString());
        try {
            service.resolveAndPublish(request);
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
