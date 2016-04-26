package com.bekioui.jaxrs.security.descriptor;

import static com.bekioui.jaxrs.security.descriptor.TokenDescriptor.Type.APPLICATION;
import static com.bekioui.jaxrs.security.descriptor.TokenDescriptor.Type.MULTI_APPLICATION;

public interface TokenDescriptor {

	enum Type {
		APPLICATION, MULTI_APPLICATION
	}

	Type getType();

	String getSecret();

	static TokenDescriptor getApplicationTokenDescriptor(String secret) {
		return new TokenDescriptor() {

			@Override
			public Type getType() {
				return APPLICATION;
			}

			@Override
			public String getSecret() {
				return secret;
			}

		};
	}

	static TokenDescriptor getMultiApplicationTokenDescriptor(String applicationIdentifier, String secret) {
		return new MultiApplicationTokenDescriptor() {

			@Override
			public Type getType() {
				return MULTI_APPLICATION;
			}

			@Override
			public String getSecret() {
				return secret;
			}

			@Override
			public String getApplicationIdentifier() {
				return applicationIdentifier;
			}

		};
	}

}
