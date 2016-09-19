package com.bekioui.jaxrs.security.descriptor;

import static com.bekioui.jaxrs.security.descriptor.ApplicationTokenDescriptor.Type.SINGLE;
import static com.bekioui.jaxrs.security.descriptor.ApplicationTokenDescriptor.Type.MULTIPLE;

public interface ApplicationTokenDescriptor {

	enum Type {
		SINGLE, MULTIPLE
	}

	Type getType();

	String getSecret();

	static ApplicationTokenDescriptor getSingleApplicationTokenDescriptor(String secret) {
		return new ApplicationTokenDescriptor() {

			@Override
			public Type getType() {
				return SINGLE;
			}

			@Override
			public String getSecret() {
				return secret;
			}

		};
	}

	static ApplicationTokenDescriptor getMultipleApplicationTokenDescriptor(String applicationIdentifier, String secret) {
		return new MultipleApplicationTokenDescriptor() {

			@Override
			public Type getType() {
				return MULTIPLE;
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
