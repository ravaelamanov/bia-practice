package com.example.monitoringconsumer.service.resolvers.user;

import com.example.monitoringconsumer.domain.Request;
import com.example.monitoringconsumer.service.resolvers.user.tokenproviders.BearerTokenProvider;
import com.example.monitoringconsumer.service.resolvers.user.tokenproviders.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUserResolver implements UserResolver {

    private final static String USER_ID_CLAIM = "user_id";

    private final TokenProvider provider;

    @Autowired
    public JwtUserResolver(BearerTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void resolve(Request request) {
        String token = provider.provide(request);
        String userId;
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token);
            userId = claims.getBody().get(USER_ID_CLAIM, String.class);
        }
        catch (ExpiredJwtException exception) {
            //TODO: do we really want to process expired tokens?
            userId = exception.getClaims().get(USER_ID_CLAIM, String.class);
        }


        request.setUserId(userId);
    }

    @Override
    public AuthType authType() {
        return AuthType.JWT;
    }
}
