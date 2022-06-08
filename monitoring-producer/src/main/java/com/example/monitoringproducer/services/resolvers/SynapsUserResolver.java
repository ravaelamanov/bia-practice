package com.example.monitoringproducer.services.resolvers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.resolvers.tokenproviders.SynapsTokenProvider;
import com.example.monitoringproducer.services.resolvers.tokenproviders.TokenProvider;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SynapsUserResolver implements UserResolver {
    private final TokenProvider provider;

    @Autowired
    public SynapsUserResolver(@Autowired SynapsTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public String resolve(Request request) {
        return "synaps";
    }

    @Override
    public AuthType authType() {
        return AuthType.SYNAPS;
    }
}
