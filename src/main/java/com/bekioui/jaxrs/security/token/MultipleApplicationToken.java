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
