package com.example.monitoringproducer.services.resolvers.tokenproviders;

import com.example.monitoringproducer.domain.Request;

public interface TokenProvider {
    String provide(Request request);
}
