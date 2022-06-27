package com.example.monitoringconsumer.services.resolvers.user;

import com.example.monitoringconsumer.messages.Request;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserResolver implements UserResolver {
    @Override
    public void resolve(Request request) {
        request.setUserId("anonymous");
        request.setUsername("anonymous");
        request.setCompanyId("anonymous");
    }

    @Override
    public AuthType authType() {
        return AuthType.DEFAULT;
    }
}
