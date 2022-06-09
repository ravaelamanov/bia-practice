package com.example.monitoringconsumer.service.resolvers.user;

import com.example.monitoringconsumer.domain.Request;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserResolver {
    void resolve(Request request);

    AuthType authType();

    @Autowired
    default void registerSelf(UserResolverRegistrar resolverRegistrar) {
        resolverRegistrar.register(authType(), this);
    }
}
