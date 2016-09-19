/**
 * Copyright (C) 2016 Mehdi Bekioui (consulting@bekioui.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bekioui.jaxrs.security.util;

import static com.bekioui.jaxrs.security.token.ApplicationToken.IDENTIFIER;
import static com.bekioui.jaxrs.security.token.ApplicationToken.ROLES;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.bekioui.jaxrs.security.token.ApplicationToken;
import com.bekioui.jaxrs.security.token.MultipleApplicationToken;

public final class JWTUtils {

    public static String create(ApplicationToken token, String secret) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFIER, token.identifier);
        claims.put(ROLES, token.roles);
        return create(claims, secret);
    }

    public static String create(MultipleApplicationToken token, String secret) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(IDENTIFIER, token.identifier);
        claims.put(ROLES, token.roles);
        return create(claims, secret);
    }

    public static ApplicationToken verifyApplicationToken(String jwt, String secret) throws Exception {
        return toApplicationToken(verify(jwt, secret));
    }

    public static MultipleApplicationToken verifyMultipleApplicationToken(String jwt, String secret) throws Exception {
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
    private static MultipleApplicationToken toMultiApplicationToken(Map<String, Object> payload) {
        return new MultipleApplicationToken((String) payload.get(IDENTIFIER), (Map<String, Set<String>>) payload.get(ROLES));
    }

}
