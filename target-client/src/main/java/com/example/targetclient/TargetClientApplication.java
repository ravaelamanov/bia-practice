package com.example.targetclient;

import com.example.targetclient.client.TargetServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class TargetClientApplication {

    private final TargetServiceClient client;

    public TargetClientApplication(TargetServiceClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        SpringApplication.run(TargetClientApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000L)
    void sendRequest() {
        int random = ThreadLocalRandom.current().nextInt(2);
        if (random == 0) {
            client.doGetAndLog();
        } else {
            client.doPostAndLog();
        }
    }

}
