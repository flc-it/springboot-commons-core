/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flcit.springboot.commons.core.util;

import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class MultiValueMapUtils {

    private MultiValueMapUtils() { }

    /**
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    public static <K, V> MultiValueMap<K, V> singleton(K key, V value) {
        final LinkedMultiValueMap<K, V> res = new LinkedMultiValueMap<>(1);
        res.add(key, value);
        return res;
    }

    /**
     * @param <K>
     * @param <T>
     * @param values
     * @return
     */
    public static <K, T> MultiValueMap<K, T> convert(final Map<K, T> values) {
        final MultiValueMap<K, T> map = new LinkedMultiValueMap<>();
        map.setAll(values);
        return map;
    }

    /**
     * @param <K>
     * @param map
     * @param name
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K> MultiValueMap<K, Object> add(MultiValueMap<K, Object> map, K name, Object value) {
        if (map == null) {
            map = new LinkedMultiValueMap<>(1);
        }
        if (value instanceof MultiValueMap<?, ?>) {
            map.addAll((MultiValueMap<K, Object>) value);
        } else if (value instanceof List<?>) {
            map.addAll(name, (List<? extends Object>) value);
        } else {
            map.add(name, value);
        }
        return map;
    }

}
