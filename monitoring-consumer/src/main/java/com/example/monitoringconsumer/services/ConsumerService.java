package com.example.monitoringconsumer.services;

import com.example.monitoringconsumer.entities.Company;
import com.example.monitoringconsumer.entities.User;
import com.example.monitoringconsumer.messages.JwtUserCreatedUpdated;
import com.example.monitoringconsumer.messages.Request;
import com.example.monitoringconsumer.messages.SynapseUserCreatedUpdated;
import com.example.monitoringconsumer.repositories.RequestRepository;
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
    private final RequestRepository repository;
    private final UserService userService;

    @Autowired
    public ConsumerService(UserResolverManager userResolverManager, RequestRepository repository, UserService userService) {
        this.userResolverManager = userResolverManager;
        this.repository = repository;
        this.userService = userService;
    }

    @Bean
    Consumer<Message<Request>> requestConsumer() {
        return requestMessage -> {

            Request request = requestMessage.getPayload();

            userResolverManager.resolve(request);
            //TODO: implement host resolution as well

            repository.save(request);
        };
    }

    @Bean
    Consumer<Message<JwtUserCreatedUpdated>> jwtUserCreatedUpdatedConsumer() {
        return jwtUserCreatedUpdatedMessage -> {
            log.info("Consuming jwt user event: " + jwtUserCreatedUpdatedMessage.getHeaders().get("event_name"));
            JwtUserCreatedUpdated jwtUserCreatedUpdated = jwtUserCreatedUpdatedMessage.getPayload();
            userService.saveOrUpdate(
                    User.builder()
                            .userId(jwtUserCreatedUpdated.getId())
                            .username(jwtUserCreatedUpdated.getUsername())
                            .company(new Company(jwtUserCreatedUpdated.getCompanyId()))
                            .build()
            );
        };
    }

    @Bean
    Consumer<Message<SynapseUserCreatedUpdated>> synapseUserCreatedUpdatedConsumer() {
        return synapseUserCreatedUpdatedMessage -> {
            log.info("Consuming synapse user event: " + synapseUserCreatedUpdatedMessage.getHeaders().get("event_name"));
            SynapseUserCreatedUpdated synapseUserCreatedUpdated = synapseUserCreatedUpdatedMessage.getPayload();
            userService.saveOrUpdate(
                    User.builder()
                            .synapseId(synapseUserCreatedUpdated.getUsername())
                            .userId(synapseUserCreatedUpdated.getUserId())
                            .build()
            );
        };
    }
}
