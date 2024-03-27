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

package org.flcit.springboot.commons.core.log.domain;

/**
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
public class BaseLogEvent<T> {

    private final T level;
    private final String message;
    private final Throwable throwable;

    /**
     * @param level
     * @param message
     */
    public BaseLogEvent(T level, String message) {
        this(level, message, null);
    }

    /**
     * @param level
     * @param message
     * @param throwable
     */
    public BaseLogEvent(T level, String message, Throwable throwable) {
        this.level = level;
        this.message = message;
        this.throwable = throwable;
    }

    /**
     * @return
     */
    public T getLevel() {
        return level;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return
     */
    public Throwable getThrowable() {
        return throwable;
    }

}
