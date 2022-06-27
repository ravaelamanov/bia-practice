package com.example.monitoringconsumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("esConfig")
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfigurationProperties {
    private String indexName;
}
