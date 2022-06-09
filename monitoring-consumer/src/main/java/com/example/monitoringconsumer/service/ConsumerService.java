package com.example.monitoringconsumer.service;

import com.example.monitoringconsumer.domain.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Configuration
@Slf4j
public class ConsumerService {
    private final UserResolverManager userResolverManager;

    @Autowired
    public ConsumerService(UserResolverManager userResolverManager) {
        this.userResolverManager = userResolverManager;
    }

    @Bean
    Consumer<Message<Request>> requestConsumer() {
        return requestMessage -> {
            Request request = requestMessage.getPayload();

            userResolverManager.resolve(request);
            //TODO: implement host resolution as well


        };
    }

}
