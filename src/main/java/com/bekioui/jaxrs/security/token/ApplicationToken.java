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
package com.bekioui.jaxrs.security.token;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationToken {

	public static final String IDENTIFIER = "identifier";

	public static final String ROLES = "roles";

	@JsonProperty(IDENTIFIER)
	public final String identifier;

	@JsonProperty(ROLES)
	public final Set<String> roles;

	@JsonCreator
	public ApplicationToken(@JsonProperty(IDENTIFIER) String identifier, @JsonProperty(ROLES) Set<String> roles) {
		this.identifier = identifier;
		this.roles = roles;
	}

}
