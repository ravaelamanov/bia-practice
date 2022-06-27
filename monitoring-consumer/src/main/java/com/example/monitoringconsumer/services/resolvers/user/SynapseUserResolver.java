package com.example.monitoringconsumer.services.resolvers.user;

import com.example.monitoringconsumer.entities.User;
import com.example.monitoringconsumer.messages.Request;
import com.example.monitoringconsumer.services.UserService;
import com.example.monitoringconsumer.services.resolvers.user.tokenproviders.SynapseTokenProvider;
import com.example.monitoringconsumer.services.resolvers.user.tokenproviders.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class SynapseUserResolver extends AbstractUserResolver {
    private final TokenProvider provider;
    private final static String USER_ID_CLAIM = "cid user_id";

    private final UserService userService;

    // example token line: 0037cid user_id = @delkogv:synapse.dev1.traffic.online
    private final static Pattern tokenLinePattern = Pattern.compile(".+ = (@.+):.+");

    @Autowired
    public SynapseUserResolver(SynapseTokenProvider provider, UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void resolve(Request request) {
        String token = provider.provide(request);
        String synapseId = extractSynapseId(token);

        User user = userService.findBySynapseId(synapseId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with synapseId: %s not found!", synapseId)));

        setRequestProperties(request, user);
    }

    private String extractSynapseId(String token) {
        return decodeBase64Url(token).lines()
                .filter(this::hasUserIdClaim)
                .findFirst()
                .flatMap(this::extractSynapseIdFromTokenLine)
                .filter(id -> !id.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("No user_id claim in this token!"));
    }

    private String decodeBase64Url(String str) {
        return new String(Base64.getUrlDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
    }

    private Optional<String> extractSynapseIdFromTokenLine(String tokenLine) {
        Matcher matcher = tokenLinePattern.matcher(tokenLine);
        if (matcher.matches()) {
            return Optional.ofNullable(matcher.group(1));
        }
        return Optional.empty();
    }

    private boolean hasUserIdClaim(String tokenLine) {
        return tokenLine.contains(USER_ID_CLAIM);
    }

    @Override
    public AuthType authType() {
        return AuthType.SYNAPSE;
    }
}
