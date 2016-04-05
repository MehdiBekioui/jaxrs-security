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
package com.bekioui.jaxrs.security.serializer;

import java.io.IOException;

import com.bekioui.jaxrs.security.context.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonSerializer {

    static <T extends Token> String serialize(T token) {
        try {
            return new ObjectMapper().writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static <T extends Token> T deserialize(String token, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(token, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
