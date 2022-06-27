package com.example.monitoringconsumer.services;

import com.example.monitoringconsumer.messages.Request;
import com.example.monitoringconsumer.services.resolvers.user.AuthType;
import com.example.monitoringconsumer.services.resolvers.user.UserResolutionException;
import com.example.monitoringconsumer.services.resolvers.user.UserResolver;
import com.example.monitoringconsumer.services.resolvers.user.UserResolverRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserResolverManager {
    private final UserResolverRegistrar userResolverRegistrar;

    @Autowired
    public UserResolverManager(UserResolverRegistrar userResolverRegistrar) {
        this.userResolverRegistrar = userResolverRegistrar;
    }

    public void resolve(Request request) {
        UserResolver resolver = userResolverRegistrar.getResolver(request.getAuthType());
        try {
            resolver.resolve(request);
        } catch (UserResolutionException exception) {
            log.error("UserResolver threw and exception:", exception);
            resolveDefault(request);
        }

        log.info("Request after user is resolved: " + request);
    }

    private void resolveDefault(Request request) {
        log.info("Resolving request using default resolver.");
        userResolverRegistrar.getResolver(AuthType.DEFAULT).resolve(request);
    }
}
