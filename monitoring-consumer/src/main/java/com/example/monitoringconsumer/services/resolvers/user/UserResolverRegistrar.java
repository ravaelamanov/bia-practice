package com.example.monitoringconsumer.services.resolvers.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserResolverRegistrar {
    private final Map<AuthType, UserResolver> resolvers;

    @Autowired
    public UserResolverRegistrar(Set<UserResolver> userResolvers) {
        this.resolvers = userResolvers.stream()
                .peek(userResolver -> log.info("Registering UserResolver for: " + userResolver.authType()))
                .collect(Collectors.toMap(
                        UserResolver::authType,
                        Function.identity(),
                        (oldResolver, newResolver) -> newResolver,
                        () -> new EnumMap<>(AuthType.class)
                ));
    }

    public UserResolver getResolver(AuthType authType) {
        return resolvers.get(authType);
    }

}
