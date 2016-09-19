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
