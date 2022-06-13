package com.example.monitoringproducer.services;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.processors.ProducerProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {
    private final ProducerProcessor processor;

    @Autowired
    public ProducerService(ProducerProcessor processor) {
        this.processor = processor;
    }

    @SneakyThrows
    public void publish(Request request) {
        processor.monitoringRequests()
                .send(MessageBuilder.withPayload(request).build()); // TODO: set timeouts
    }


}
