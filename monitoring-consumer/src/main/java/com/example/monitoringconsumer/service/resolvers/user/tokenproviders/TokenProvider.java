package com.example.monitoringconsumer.service.resolvers.user.tokenproviders;

import com.example.monitoringconsumer.domain.Request;

public interface TokenProvider {
    String provide(Request request);
}
