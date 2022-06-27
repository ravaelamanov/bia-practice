package com.example.monitoringconsumer.messages.deserialization;

import com.example.monitoringconsumer.messages.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class RequestMessageConverter extends AbstractMessageConverter {
    private final ObjectMapper mapper;

    @Autowired
    public RequestMessageConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    @SneakyThrows
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        byte[] payload = (byte[]) message.getPayload();

        return mapper.readValue(payload, Request.class);
    }
}
