package com.bekioui.jaxrs.security.util;

import static com.bekioui.jaxrs.security.context.ApplicationToken.IDENTIFIER;
import static com.bekioui.jaxrs.security.context.ApplicationToken.ROLES;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.bekioui.jaxrs.security.context.ApplicationToken;
import com.bekioui.jaxrs.security.context.MultiApplicationToken;

public final class JWTUtils {

    public static String create(ApplicationToken token, String secret) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFIER, token.identifier);
        claims.put(ROLES, token.roles);
        return create(claims, secret);
    }

    public static String create(MultiApplicationToken token, String secret) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFIER, token.identifier);
        claims.put(ROLES, token.roles);
        return create(claims, secret);
    }

    public static ApplicationToken verifyApplicationToken(String jwt, String secret) throws Exception {
        return toApplicationToken(verify(jwt, secret));
    }

    public static MultiApplicationToken verifyMultiApplicationToken(String jwt, String secret) throws Exception {
        return toMultiApplicationToken(verify(jwt, secret));
    }

    private static String create(Map<String, Object> claims, String secret) {
        return new JWTSigner(secret).sign(claims);
    }

    private static Map<String, Object> verify(String jwt, String secret) throws Exception {
        return new JWTVerifier(secret, "audience").verify(jwt);
    }

    @SuppressWarnings("unchecked")
    private static ApplicationToken toApplicationToken(Map<String, Object> payload) {
        return new ApplicationToken((String) payload.get(IDENTIFIER), (Set<String>) payload.get(ROLES));
    }

    @SuppressWarnings("unchecked")
    private static MultiApplicationToken toMultiApplicationToken(Map<String, Object> payload) {
        return new MultiApplicationToken((String) payload.get(IDENTIFIER), (Map<String, Set<String>>) payload.get(ROLES));
    }

}
