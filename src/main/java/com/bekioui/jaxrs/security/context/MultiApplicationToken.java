package com.bekioui.jaxrs.security.context;

import static com.bekioui.jaxrs.security.context.ApplicationToken.IDENTIFIER;
import static com.bekioui.jaxrs.security.context.ApplicationToken.ROLES;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultiApplicationToken {

    @JsonProperty(IDENTIFIER)
    public final String identifier;

    @JsonProperty(ROLES)
    public final Map<String, Set<String>> roles;

    @JsonCreator
    public MultiApplicationToken(@JsonProperty(IDENTIFIER) String identifier, @JsonProperty(ROLES) Map<String, Set<String>> roles) {
        this.identifier = identifier;
        this.roles = roles;
    }

}
