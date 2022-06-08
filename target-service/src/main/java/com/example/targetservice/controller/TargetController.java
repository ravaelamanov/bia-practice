package com.example.targetservice.controller;

import com.example.targetservice.dto.ResourceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/**")
public class TargetController {
    @GetMapping
    ResourceDto sampleGet() {
//        delay(3);
        return new ResourceDto(UUID.randomUUID().toString());
    }

    @PostMapping
    ResourceDto samplePost() {
//        delay(3);
        return new ResourceDto(UUID.randomUUID().toString());
    }

    private void delay(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
