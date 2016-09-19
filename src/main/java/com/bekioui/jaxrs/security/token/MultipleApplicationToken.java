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

import static com.bekioui.jaxrs.security.token.ApplicationToken.IDENTIFIER;
import static com.bekioui.jaxrs.security.token.ApplicationToken.ROLES;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultipleApplicationToken {

    @JsonProperty(IDENTIFIER)
    public final String identifier;

    @JsonProperty(ROLES)
    public final Map<String, Set<String>> roles;

    @JsonCreator
    public MultipleApplicationToken(@JsonProperty(IDENTIFIER) String identifier, @JsonProperty(ROLES) Map<String, Set<String>> roles) {
        this.identifier = identifier;
        this.roles = roles;
    }

}
