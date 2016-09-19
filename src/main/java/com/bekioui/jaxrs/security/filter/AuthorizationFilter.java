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
package com.bekioui.jaxrs.security.filter;

import static com.bekioui.jaxrs.security.util.JWTUtils.verifyApplicationToken;
import static com.bekioui.jaxrs.security.util.JWTUtils.verifyMultipleApplicationToken;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bekioui.jaxrs.security.context.CustomSecurityContext;
import com.bekioui.jaxrs.security.descriptor.MultipleApplicationTokenDescriptor;
import com.bekioui.jaxrs.security.descriptor.ApplicationTokenDescriptor;
import com.bekioui.jaxrs.security.token.ApplicationToken;
import com.bekioui.jaxrs.security.token.MultipleApplicationToken;
import com.excilys.ebi.utils.spring.log.slf4j.InjectLogger;

@Component
@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public final class AuthorizationFilter implements ContainerRequestFilter {

    private static final String BEARER = "Bearer ";

    @InjectLogger
    private Logger logger;

    @Autowired(required = false)
    private ApplicationTokenDescriptor tokenDescriptor;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void postConstruct() {
        if (tokenDescriptor == null) {
            logger.info("No token descriptor was defined.");
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString(AUTHORIZATION);

        if (authorization == null || tokenDescriptor == null) {
            return;
        }

        if (!authorization.startsWith(BEARER)) {
            abort(requestContext, "Authorization header must use Bearer schema.");
            return;
        }

        String jwt = authorization.substring(BEARER.length());

        try {
            switch (tokenDescriptor.getType()) {
            case SINGLE:
                ApplicationToken applicationToken = verifyApplicationToken(jwt, tokenDescriptor.getSecret());
                requestContext.setSecurityContext(new CustomSecurityContext(applicationToken));
                break;
            case MULTIPLE:
                MultipleApplicationTokenDescriptor descriptor = (MultipleApplicationTokenDescriptor) tokenDescriptor;
                MultipleApplicationToken multipleApplicationToken = verifyMultipleApplicationToken(jwt, tokenDescriptor.getSecret());
                requestContext.setSecurityContext(new CustomSecurityContext(multipleApplicationToken, descriptor.getApplicationIdentifier()));
                break;
            default:
                abort(requestContext, "Unknown token type descriptor: " + tokenDescriptor.getType());
                return;
            }
        } catch (Exception e) {
            String message = "Failed to decode token.";
            logger.error(message, e);
            abort(requestContext, message);
        }
    }

    private void abort(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(Response.status(UNAUTHORIZED).entity(message).build());
    }

}
