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

import org.flcit.commons.core.util.FunctionUtils;
import org.flcit.commons.core.util.ObjectUtils;
import org.springframework.core.log.LogFormatUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class LogUtils {

    private LogUtils() { }

    /**
     * @param value
     * @param maxLength
     * @return
     */
    public static String formatValue(String value, Integer maxLength) {
        return formatValue(value, maxLength, true);
    }

    /**
     * @param value
     * @param maxLength
     * @param replaceNewlinesAndControlCharacters
     * @return
     */
    public static String formatValue(String value, Integer maxLength, boolean replaceNewlinesAndControlCharacters) {
            return FunctionUtils.safeNoSuchMethod(
                () -> newFormatValue(value, maxLength, replaceNewlinesAndControlCharacters),
                () -> oldFormatValue(value, maxLength)
            );
    }

    private static String newFormatValue(String value, Integer maxLength, boolean replaceNewlinesAndControlCharacters) {
        return LogFormatUtils.formatValue(value, ObjectUtils.getOrDefault(maxLength, -1), replaceNewlinesAndControlCharacters);
    }

    private static String oldFormatValue(String value, Integer maxLength ) {
        return LogFormatUtils.formatValue(value, maxLength != null && maxLength > 0);
    }

}
