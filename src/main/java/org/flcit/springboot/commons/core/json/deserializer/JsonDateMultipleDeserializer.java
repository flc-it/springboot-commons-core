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

package org.flcit.springboot.commons.core.json.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.flcit.commons.core.util.DateUtils;
import org.flcit.springboot.commons.core.json.JsonDateMultipleFormat;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public class JsonDateMultipleDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {

    private final String[] patterns;
    private final SimpleDateFormat[] formats;

    private JsonDateMultipleDeserializer() {
        this.patterns = null;
        this.formats = null;
    }

    private JsonDateMultipleDeserializer(String[] patterns, Locale locale, TimeZone timezone) {
        this.patterns = Arrays.copyOf(patterns, patterns.length);
        this.formats = new SimpleDateFormat[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            this.formats[i] = build(patterns[i], locale, timezone);
        }
    }

    private static final SimpleDateFormat build(String pattern, Locale locale, TimeZone timezone) {
        final SimpleDateFormat format = locale != null ? new SimpleDateFormat(pattern, locale) : new SimpleDateFormat(pattern);
        format.setTimeZone(timezone != null ? timezone : DateUtils.UTC);
        return format;
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        final String date = p.getText();
        if (!StringUtils.hasLength(date)) {
            return null;
        }
        for (int i = 0; i < this.patterns.length; i++) {
            if (this.patterns[i].length() >= date.length()) {
                try {
                    return this.formats[i].parse(date);
                } catch (ParseException e) {
                    // DO NOTHING
                }
            }
        }
        return (Date) ctxt.handleWeirdStringValue(handledType(), date,
                "expected one formats : \"%s\"", String.join(", ", patterns));
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        final JsonDateMultipleFormat annotation = getAnnotation(property);
        String[] annotationPatterns = annotation.value();
        if (ObjectUtils.isEmpty(annotationPatterns)) {
            annotationPatterns = annotation.patterns();
        }
        final Locale locale = StringUtils.hasLength(annotation.locale()) ? new Locale(annotation.locale()) : null;
        final TimeZone timezone = StringUtils.hasLength(annotation.timezone()) ? TimeZone.getTimeZone(annotation.timezone()) : null;
        return new JsonDateMultipleDeserializer(annotationPatterns, locale, timezone);
    }

    private static final JsonDateMultipleFormat getAnnotation(BeanProperty property) {
        return property.getAnnotation(JsonDateMultipleFormat.class);
    }

}