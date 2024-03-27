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

package org.flcit.springboot.commons.core.json;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.flcit.commons.core.util.StringUtils;
import org.flcit.springboot.commons.core.json.deserializer.JsonDateMultipleDeserializer;
import org.springframework.core.annotation.AliasFor;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD, PARAMETER, METHOD })
@JacksonAnnotationsInside
@JsonDeserialize(using = JsonDateMultipleDeserializer.class)
public @interface JsonDateMultipleFormat {

    /**
     * @return
     */
    @AliasFor("value")
    String[] patterns() default {};

    /**
     * @return
     */
    @AliasFor("patterns")
    String[] value() default {};

    /**
     * @return
     */
    String locale() default StringUtils.EMPTY;

    /**
     * @return
     */
    String timezone() default StringUtils.EMPTY;

}
