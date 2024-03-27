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

import org.springframework.util.StringUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class OpenApiUtils {

    private OpenApiUtils() { }

    /**
     * @return
     */
    public static String[] getDocsEndpoints() {
        return new String[] { "/v3/api-docs/**" };
    }

    /**
     * @param group
     * @return
     */
    public static String getDocsEndpoint(String group) {
        return "/v3/api-docs/" + group;
    }

    /**
     * @param groups
     * @return
     */
    public static String[] getDocsEndpoints(String... groups) {
        final String[] names = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            names[i] = getDocsEndpoint(groups[i]);
        }
        return names;
    }

    /**
     * @return
     */
    public static String[] getUIEndpoints() {
        return new String[] { "/swagger-ui.html", "/swagger-ui/**" };
    }

    /**
     * @return
     */
    public static String[] getAllEndpoints() {
        return StringUtils.concatenateStringArrays(getDocsEndpoints(), getUIEndpoints());
    }

}
