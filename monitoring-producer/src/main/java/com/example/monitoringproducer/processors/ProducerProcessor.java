package com.example.monitoringproducer.processors;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerProcessor {
    String OUTPUT = "requests";

    @Output(OUTPUT)
    MessageChannel requests();
}
