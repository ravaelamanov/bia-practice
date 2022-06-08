package com.example.monitoringproducer.services;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.processors.ProducerProcessor;
import com.example.monitoringproducer.services.resolvers.UserResolver;
import com.example.monitoringproducer.services.resolvers.UserResolverRegistrar;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {
    private final UserResolverRegistrar resolverRegistrar;
    private final ProducerProcessor processor;

    @Autowired
    public ProducerService(UserResolverRegistrar resolverRegistrar, ProducerProcessor processor) {
        this.resolverRegistrar = resolverRegistrar;
        this.processor = processor;
    }
    public void resolveAndPublish(Request request) {
        resolve(request);
        log.info("Resolved userId: " + request.getUserId());

        publish(request);
        log.info("Published request is: " + request);
    }

    private void resolve(Request request) {
        UserResolver resolver = resolverRegistrar.getResolver(request.getAuthType());
        String userId;
        try {
            userId = resolver.resolve(request);
        }
        catch (Exception exception) {
            userId = resolverRegistrar.getResolver(AuthType.DEFAULT).resolve(request);
        }
        request.setUserId(userId);
    }

    private void publish(Request request) {
        processor.monitoringRequests()
                .send(MessageBuilder.withPayload(request).build());
    }
}
