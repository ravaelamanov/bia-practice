package com.example.monitoringconsumer.service;

import com.example.monitoringconsumer.domain.Request;
import com.example.monitoringconsumer.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Configuration
@Slf4j
public class ConsumerService {
    private final UserResolverManager userResolverManager;
    private final RequestRepository repository;

    @Autowired
    public ConsumerService(UserResolverManager userResolverManager, RequestRepository repository) {
        this.userResolverManager = userResolverManager;
        this.repository = repository;
    }

    @Bean
    Consumer<Request> requestConsumer() {
        return request -> {

            userResolverManager.resolve(request);
            //TODO: implement host resolution as well

            repository.save(request);
        };
    }

}
