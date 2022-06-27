package com.example.monitoringconsumer.messages.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
public class MultiValueMapDeserializer extends JsonDeserializer<MultiValueMap<String, String>> {

    @Override
    public MultiValueMap<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return p.readValueAs(new TypeReference<LinkedMultiValueMap<String, String>>() {});
    }
}
