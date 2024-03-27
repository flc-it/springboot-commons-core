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

package org.flcit.springboot.commons.core.condition;

import org.flcit.springboot.commons.core.util.EnvironmentUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public class EnvironmentCondition implements Condition {

    /**
     *
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final MultiValueMap<String, Object> attrs = metadata.getAllAnnotationAttributes(ConditionalOnEnvironment.class.getName());
        if (attrs != null) {
            final boolean notIn = (boolean) attrs.getFirst("notIn");
            final String environnement = EnvironmentUtils.getEnvironment(context.getEnvironment());
            for (String value : (String[]) attrs.getFirst("value")) {
                if (environnement.equals(value)) {
                    return !notIn;
                }
            }
            return notIn;
        }
        return true;
    }

}
