package com.example.monitoringconsumer.domain;

import com.example.monitoringconsumer.service.resolvers.user.AuthType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "request")
public class Request implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String uri;
    private String clientIpAddress;
    @JsonDeserialize(using = MultiValueMapDeserializer.class)
    private MultiValueMap<String, String> headers;
    @JsonDeserialize(using = MultiValueMapDeserializer.class)
    private MultiValueMap<String, String> query;
    private AuthType authType;
    @Field(type = FieldType.Keyword)
    private String targetName;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(name = "@timestamp", type = FieldType.Date, format = DateFormat.date_time)
    private Instant timestamp;

    public Optional<String> getHeader(String header) {
        return findFirstFromMultiValueMap(headers, header);
    }

    public Optional<String> getQuery(String query) {
        return findFirstFromMultiValueMap(this.query, query);
    }

    private static <K, V> Optional<V> findFirstFromMultiValueMap(MultiValueMap<K, V> multiValueMap, K key) {
        return Optional.ofNullable(multiValueMap)
                .map(map -> map.get(key))
                .map(List::stream)
                .flatMap(Stream::findFirst);
    }
}
