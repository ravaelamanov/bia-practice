package com.example.monitoringproducer.services.resolvers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserResolver {
    String resolve(Request request);

    AuthType authType();

    @Autowired
    default void registerSelf(UserResolverRegistrar resolverRegistrar) {
        resolverRegistrar.register(authType(), this);
    }
}
