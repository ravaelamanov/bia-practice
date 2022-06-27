package com.example.monitoringconsumer.messages;

import com.example.monitoringconsumer.messages.deserialization.MultiValueMapDeserializer;
import com.example.monitoringconsumer.services.resolvers.user.AuthType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "#{@esConfig.getIndexName()}")
@Mapping
public class Request {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String uri;
    @Field(type = FieldType.Keyword)
    private String httpMethod;
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
    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Keyword)
    private String companyId;
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
