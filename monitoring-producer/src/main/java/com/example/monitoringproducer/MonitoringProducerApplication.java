package com.example.monitoringproducer;

import com.example.monitoringproducer.processors.ProducerProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(ProducerProcessor.class)
public class MonitoringProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringProducerApplication.class, args);
    }

}
