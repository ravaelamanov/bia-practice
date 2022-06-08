package com.example.monitoringproducer.services.resolvers;

import com.example.monitoringproducer.domain.Request;
import com.example.monitoringproducer.services.resolvers.tokenproviders.JwtTokenProvider;
import com.example.monitoringproducer.services.resolvers.tokenproviders.TokenProvider;
import com.example.monitoringproducer.services.resolvers.util.AuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUserResolver implements UserResolver {

    private final static String USER_ID_CLAIM = "user_id";

    private final TokenProvider provider;

    @Autowired
    public JwtUserResolver(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public String resolve(Request request) {
/*        String token = provider.provide(request);

        Jws<Claims> claims = Jwts.parser().parseClaimsJws(token);

        return claims.getBody().get(USER_ID_CLAIM, String.class);*/

        return "jwt";

    }

    @Override
    public AuthType authType() {
        return AuthType.JWT;
    }
}
