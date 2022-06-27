package com.example.monitoringconsumer.services.resolvers.user;

import com.example.monitoringconsumer.entities.User;
import com.example.monitoringconsumer.messages.Request;
import com.example.monitoringconsumer.services.UserService;
import com.example.monitoringconsumer.services.resolvers.user.tokenproviders.BearerTokenProvider;
import com.example.monitoringconsumer.services.resolvers.user.tokenproviders.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JwtUserResolver extends AbstractUserResolver {

    private final static String USER_ID_CLAIM = "user_id";

    private final TokenProvider provider;

    private final UserService userService;

    @Autowired
    public JwtUserResolver(BearerTokenProvider provider, UserService userService) {
        this.provider = provider;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void resolve(Request request) {
        String token = provider.provide(request);
        String userId = extractUserId(token);

        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with userId: %s not found!", userId)));

        setRequestProperties(request, user);
    }

    private String extractUserId(String token) {
        String userId;
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token);
            userId = claims.getBody().get(USER_ID_CLAIM, String.class);
        }
        catch (ExpiredJwtException exception) {
            userId = exception.getClaims().get(USER_ID_CLAIM, String.class);
        }
        return userId;
    }

    @Override
    public AuthType authType() {
        return AuthType.JWT;
    }
}
