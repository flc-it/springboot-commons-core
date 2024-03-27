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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import org.flcit.commons.core.util.BooleanUtils;
import org.flcit.commons.core.util.FunctionUtils;
import org.flcit.commons.core.util.NumberUtils;
import org.springframework.cache.Cache;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class PropertyUtils {

    private PropertyUtils() { }

    /**
     * @param <T>
     * @param cache
     * @param name
     * @param propertyResolver
     * @param propertySources
     * @param propertySourcesNames
     * @param converter
     * @param defaultValue
     * @return
     */
    public static <T> T getValue(Cache cache, String name, PropertyResolver propertyResolver, PropertySources propertySources, String[] propertySourcesNames, Function<Object, T> converter, Supplier<T> defaultValue) {
        if (cache != null) {
            return cache.get(
                    PropertyUtils.class.getName() + "_" + name,
                    () -> org.flcit.commons.core.util.ObjectUtils.getOrDefault(
                            FunctionUtils.convertIfNotNull(getValue(propertyResolver, propertySources, name, propertySourcesNames), converter),
                            defaultValue
                    )
            );
        } else {
            return org.flcit.commons.core.util.ObjectUtils.getOrDefault(
                    FunctionUtils.convertIfNotNull(getValue(propertyResolver, propertySources, name, propertySourcesNames), converter),
                    defaultValue
            );
        }
    }

    private static Object getValue(PropertyResolver propertyResolver, PropertySources propertySources, String name, String[] propertySourcesNames) {
        if (ObjectUtils.isEmpty(propertySourcesNames)) {
            return propertyResolver.getProperty(name);
        }
        for (String propertySource: propertySourcesNames) {
            Object value = getValue(propertySources, name, propertySource);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private static Object getValue(PropertySources propertySources, String name, String propertySource) {
        if (!StringUtils.hasLength(propertySource)) {
            return null;
        }
        final PropertySource<?> source = propertySources.get(propertySource);
        return source != null ? source.getProperty(name) : null;
    }

    /**
     * @param properties
     * @param property
     * @return
     */
    public static Boolean getBoolean(Properties properties, String property) {
        return toBoolean(properties.get(property));
    }

    /**
     * @param properties
     * @param property
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(Properties properties, String property, Boolean defaultValue) {
        return toBoolean(properties.getOrDefault(property, defaultValue));
    }

    /**
     * @param value
     * @return
     */
    public static Boolean toBoolean(Object value) {
        return toBoolean(value, null);
    }

    /**
     * @param value
     * @param defaultValue
     * @return
     */
    public static Boolean toBoolean(Object value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return toBoolean(value.toString(), defaultValue);
    }

    /**
     * @param value
     * @param defaultValue
     * @return
     */
    public static Boolean toBoolean(String value, Boolean defaultValue) {
        return StringUtils.hasLength(value) ?
                org.flcit.commons.core.util.ObjectUtils.getOrDefault(BooleanUtils.parse(value), defaultValue)
                : defaultValue;
    }

    /**
     * @param properties
     * @param property
     * @return
     */
    public static List<String> getStringList(Properties properties, String property) {
        return getStringList(properties, property, null);
    }

    /**
     * @param properties
     * @param property
     * @param defaultValue
     * @return
     */
    public static List<String> getStringList(Properties properties, String property, List<String> defaultValue) {
        return toList(properties.getProperty(property), defaultValue);
    }

    /**
     * @param value
     * @return
     */
    public static List<String> toList(String value) {
        return toList(value, null);
    }

    /**
     * @param value
     * @param defaultValue
     * @return
     */
    public static List<String> toList(String value, List<String> defaultValue) {
        return value != null ? Arrays.asList(toArray(value)) : defaultValue;
    }

    /**
     * @param properties
     * @param property
     * @return
     */
    public static String[] getStringArray(Properties properties, String property) {
        return getStringArray(properties, property, null);
    }

    /**
     * @param properties
     * @param property
     * @param defaultValue
     * @return
     */
    public static String[] getStringArray(Properties properties, String property, String[] defaultValue) {
        return toArray(properties.getProperty(property), defaultValue);
    }

    /**
     * @param value
     * @return
     */
    public static String[] toArray(String value) {
        return toArray(value, null);
    }

    /**
     * @param value
     * @param defaultValue
     * @return
     */
    public static String[] toArray(String value, String[] defaultValue) {
        return value != null ? value.split(",") : defaultValue;
    }

    /**
     * @param <T>
     * @param properties
     * @param property
     * @param classType
     * @return
     */
    public static <T extends Number> T getNumber(Properties properties, String property, Class<T> classType) {
        return toNumber(properties.get(property), classType);
    }

    /**
     * @param <T>
     * @param properties
     * @param property
     * @param defaultValue
     * @param classType
     * @return
     */
    public static <T extends Number> T getNumber(Properties properties, String property, T defaultValue, Class<T> classType) {
        return toNumber(properties.getOrDefault(property, defaultValue), classType);
    }

    /**
     * @param <T>
     * @param value
     * @param classType
     * @return
     */
    public static <T extends Number> T toNumber(Object value, Class<T> classType) {
        return toNumber(value, null, classType);
    }

    /**
     * @param <T>
     * @param value
     * @param defaultValue
     * @param classType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T toNumber(Object value, T defaultValue, Class<T> classType) {
        if (value == null) {
            return defaultValue;
        }
        if (classType.getClass().isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        if (Number.class.isAssignableFrom(value.getClass()) ) {
            return NumberUtils.convert((Number) value, classType);
        }
        return NumberUtils.convertIndependant(value.toString(), classType);
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public static Map<String, String> load(Path path) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path)) {
            return load(inputStream);
        }
    }

    /**
     * @param inputStream
     * @return
     * @throws IOException
     */
    @SuppressWarnings("java:S1168")
    public static Map<String, String> load(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        final Properties properties = new Properties();
        properties.load(inputStream);
        if (properties.isEmpty()) {
            return null;
        }
        final Map<String, String> propertyMap = new HashMap<>(properties.size());
        for (String propertyName : properties.stringPropertyNames()) {
            propertyMap.put(propertyName, properties.getProperty(propertyName));
        }
        return propertyMap;
    }

    /**
     * @param propertySources
     * @param clazzToRemove
     * @param excludeNames
     */
    public static void remove(final PropertySources propertySources, Class<? extends PropertySource<?>> clazzToRemove, String... excludeNames) {
        if (propertySources == null
                || clazzToRemove == null) {
            return;
        }
        final Iterator<PropertySource<?>> it = propertySources.iterator();
        while (it.hasNext()) {
            final PropertySource<?> propertySource = it.next();
            if (propertySource.getClass().equals(clazzToRemove)
                    && (ObjectUtils.isEmpty(excludeNames)
                    || !ObjectUtils.containsElement(excludeNames, propertySource.getName()))) {
                it.remove();
            }
        }
    }

    /**
     * @param propertySources
     * @param prefixName
     */
    public static void remove(final PropertySources propertySources, String prefixName) {
        if (propertySources == null
                || prefixName == null) {
            return;
        }
        final Iterator<PropertySource<?>> it = propertySources.iterator();
        while (it.hasNext()) {
            if (it.next().getName().startsWith(prefixName)) {
                it.remove();
            }
        }
    }

    /**
     * @param propertySources
     * @param name
     * @param propertySource
     */
    public static void addFirstOrReplace(final MutablePropertySources propertySources, String name, PropertySource<?> propertySource) {
        addOrReplace(propertySources, name, propertySource, true);
    }

    /**
     * @param propertySources
     * @param name
     * @param propertySource
     */
    public static void addLastOrReplace(final MutablePropertySources propertySources, String name, PropertySource<?> propertySource) {
        addOrReplace(propertySources, name, propertySource, false);
    }

    private static void addOrReplace(final MutablePropertySources propertySources, String name, PropertySource<?> propertySource, boolean first) {
        if (propertySources == null
                || name == null) {
            return;
        }
        if (propertySources.contains(name)) {
            propertySources.replace(name, propertySource);
        } else {
            if (first) {
                propertySources.addFirst(propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }

}
