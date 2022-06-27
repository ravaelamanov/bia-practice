package com.example.monitoringconsumer.services.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.messages.Request;

public interface TokenProvider {
    String provide(Request request);
}
