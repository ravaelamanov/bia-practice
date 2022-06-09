package com.example.monitoringconsumer.service.resolvers.user;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserResolver implements UserResolver {
    @Override
    public void resolve(Request request) {
        request.setUserId("default");
    }

    @Override
    public AuthType authType() {
        return AuthType.DEFAULT;
    }
}
