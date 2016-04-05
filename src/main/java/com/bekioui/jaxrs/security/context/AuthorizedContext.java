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

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class AuthorizedContext implements SecurityContext {

    private final Token token;

    public AuthorizedContext(Token token) {
        this.token = token;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> token.identifier;
    }

    @Override
    public boolean isUserInRole(String role) {
        return token.roles.contains(role);
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
