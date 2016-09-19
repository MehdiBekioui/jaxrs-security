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
package com.bekioui.jaxrs.security.context;

import static java.util.Objects.requireNonNull;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.SecurityContext;

import com.bekioui.jaxrs.security.token.ApplicationToken;
import com.bekioui.jaxrs.security.token.MultipleApplicationToken;

public class CustomSecurityContext implements SecurityContext {

    private final String identifier;

    private final Set<String> roles;

    public CustomSecurityContext() {
        this.identifier = "Anonymous";
        this.roles = new HashSet<>();
    }

    public CustomSecurityContext(ApplicationToken token) {
        this.identifier = token.identifier;
        this.roles = token.roles;
    }

    public CustomSecurityContext(MultipleApplicationToken token, String applicationIdentifier) {
        this.identifier = token.identifier;
        this.roles = new HashSet<>(token.roles.get(applicationIdentifier));
        requireNonNull(roles);
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> identifier;
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.FORM_AUTH;
    }

}
