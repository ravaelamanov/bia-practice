package com.example.monitoringconsumer;

import com.example.monitoringconsumer.config.ElasticsearchConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties(ElasticsearchConfigurationProperties.class)
@EnableAspectJAutoProxy
public class MonitoringConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringConsumerApplication.class, args);
    }
}
