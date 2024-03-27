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

import org.flcit.commons.core.functional.consumer.TriConsumer;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class EnvironmentPostProcessorLogSlf4jUtils {

    private EnvironmentPostProcessorLogSlf4jUtils() { }

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
            logger.error(message, e);
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
            logger.error(message, e);
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
            log(logger, level, message, null);
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
            log(logger, level, message, null);
        }
    }

    /**
     * @param log
     * @param level
     * @param message
     * @param throwable
     */
    public static void log(Logger log, Level level, String message, Throwable throwable) {
        switch (level) {
        case TRACE:
            log.trace(message, throwable);
            break;
        case DEBUG:
            log.debug(message, throwable);
            break;
        case INFO:
            log.info(message, throwable);
            break;
        case WARN:
            log.warn(message, throwable);
            break;
        case ERROR:
            log.error(message, throwable);
            break;
        }
    }

}
