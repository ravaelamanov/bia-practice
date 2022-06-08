package com.example.monitoringproducer.services.resolvers.tokenproviders;

import com.example.monitoringproducer.domain.Request;
import org.springframework.stereotype.Component;

@Component
public class SynapsTokenProvider implements TokenProvider {
    @Override
    public String provide(Request request) {
        return null;
    }
}
