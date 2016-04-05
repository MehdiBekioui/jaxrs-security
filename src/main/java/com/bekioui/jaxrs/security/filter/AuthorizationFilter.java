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

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bekioui.jaxrs.security.context.AuthorizedContext;
import com.bekioui.jaxrs.security.context.Token;
import com.bekioui.jaxrs.security.descriptor.HasherDescriptor;
import com.bekioui.jaxrs.security.descriptor.TokenDescriptor;
import com.bekioui.jaxrs.security.hash.Base64Hasher;
import com.bekioui.jaxrs.security.hash.Hasher;
import com.bekioui.jaxrs.security.serializer.JsonSerializer;

@Component
@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public final class AuthorizationFilter implements ContainerRequestFilter {

	@Autowired(required = false)
	private TokenDescriptor tokenDescriptor;

	@Autowired(required = false)
	private HasherDescriptor hasherDescriptor;

	@Autowired
	private ApplicationContext applicationContext;

	private Class<? extends Token> clazz;

	private Hasher<? extends Token> hasher;

	@PostConstruct
	private void postConstruct() {
		clazz = (tokenDescriptor != null ? tokenDescriptor : TokenDescriptor.getDefault()).getTokenClass();
		if (hasherDescriptor != null) {
			switch (hasherDescriptor.getType()) {
			case BASE64:
				hasher = new Base64Hasher<>(clazz);
				break;
			}
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext) {
		String authorization = requestContext.getHeaderString(AUTHORIZATION);
		if (authorization != null) {
			Token token = hasher != null ? hasher.decode(authorization) : JsonSerializer.deserialize(authorization, clazz);
			if (token != null) {
				requestContext.setSecurityContext(new AuthorizedContext(token));
			}
		}
	}

}
