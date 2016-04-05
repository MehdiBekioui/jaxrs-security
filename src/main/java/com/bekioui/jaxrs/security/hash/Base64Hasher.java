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
package com.bekioui.jaxrs.security.hash;

import com.bekioui.jaxrs.security.context.Token;
import com.bekioui.jaxrs.security.serializer.JsonSerializer;
import com.google.common.io.BaseEncoding;

public class Base64Hasher<T extends Token> implements Hasher<T> {

    private final BaseEncoding baseEncoding = BaseEncoding.base64();

    private final Class<T> clazz;

    public Base64Hasher(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String encode(T token) {
        return baseEncoding.encode(JsonSerializer.serialize(token).getBytes());
    }

    @Override
    public T decode(String token) {
        return JsonSerializer.deserialize(new String(baseEncoding.decode(token)), clazz);
    }

}
