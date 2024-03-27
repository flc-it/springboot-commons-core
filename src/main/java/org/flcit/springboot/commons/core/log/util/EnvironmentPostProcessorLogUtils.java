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

package org.flcit.springboot.commons.core.log.util;

import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.flcit.commons.core.functional.consumer.TriConsumer;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class EnvironmentPostProcessorLogUtils {

    private EnvironmentPostProcessorLogUtils() { }

    /**
     * @param logger
     * @param consumer
     * @param message
     * @param e
     */
    public static void write(Logger logger, BiConsumer<String, Throwable> consumer, String message, Exception e) {
        if (consumer != null) {
            consumer.accept(message, e);
        } else {
            logger.log(Level.SEVERE, message, e);
        }
    }

    /**
     * @param logger
     * @param consumer
     * @param message
     * @param e
     */
    public static void write(Logger logger, TriConsumer<Logger, String, Throwable> consumer, String message, Exception e) {
        if (consumer != null) {
            consumer.accept(logger, message, e);
        } else {
            logger.log(Level.SEVERE, message, e);
        }
    }

    /**
     * @param logger
     * @param consumer
     * @param level
     * @param message
     */
    public static void write(Logger logger, BiConsumer<Level, String> consumer, Level level, String message) {
        if (consumer != null) {
            consumer.accept(level, message);
        } else {
            logger.log(level, message);
        }
    }

    /**
     * @param logger
     * @param consumer
     * @param level
     * @param message
     */
    public static void write(Logger logger, TriConsumer<Logger, Level, String> consumer, Level level, String message) {
        if (consumer != null) {
            consumer.accept(logger, level, message);
        } else {
            logger.log(level, message);
        }
    }

}
