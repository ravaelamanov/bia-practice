package com.example.monitoringproducer.services.resolvers;

import com.example.monitoringproducer.services.resolvers.util.AuthType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
@Slf4j
public class UserResolverRegistrar {
    private final EnumMap<AuthType, UserResolver> resolvers = new EnumMap<>(AuthType.class);

    public void register(AuthType authType, UserResolver resolver) {
        log.info("Registering UserResolver for " + authType.toString());
        resolvers.put(authType, resolver);
    }

    public UserResolver getResolver(AuthType authType) {
        return resolvers.get(authType);
    }

}
