package com.example.monitoringproducer.services.resolvers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserResolver implements UserResolver {
    @Override
    public String resolve(Request request) {
        return "default";
    }

    @Override
    public AuthType authType() {
        return AuthType.DEFAULT;
    }
}
