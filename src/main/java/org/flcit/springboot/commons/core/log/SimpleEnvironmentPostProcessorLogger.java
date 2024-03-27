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

package org.flcit.springboot.commons.core.log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.flcit.springboot.commons.core.log.domain.BaseLogEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.util.CollectionUtils;

/**
 * @param <L>
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
public abstract class SimpleEnvironmentPostProcessorLogger<L, T> {

    private List<BaseLogEvent<T>> logs;
    private BiConsumer<String, Throwable> consumerError;
    private BiConsumer<T, String> consumerMessage;

    private synchronized void addLogs(T level, String message, Throwable throwable) {
        if (this.logs == null) {
            this.logs = new ArrayList<>(1);
        }
        this.logs.add(new BaseLogEvent<>(level, message, throwable));
    }

    /**
     * @return
     */
    public BiConsumer<String, Throwable> getConsumerError() {
        if (this.consumerError == null) {
            this.consumerError = this::addError;
        }
        return this.consumerError;
    }

    /**
     * @param message
     * @param e
     */
    public void addError(String message, Throwable e) {
        this.addLogs(getLevelError(), message, e);
    }

    /**
     * @return
     */
    public BiConsumer<T, String> getConsumerMessage() {
        if (this.consumerMessage == null) {
            this.consumerMessage = this::addMessage;
        }
        return this.consumerMessage;
    }

    /**
     * @param level
     * @param message
     */
    public void addMessage(T level, String message) {
        this.addLogs(level, message, null);
    }

    /**
     * @param application
     * @param logger
     */
    public void write(final SpringApplication application, final L logger) {
        this.consumerError = null;
        this.consumerMessage = null;
        if (CollectionUtils.isEmpty(logs)) {
            this.logs = null;
            return;
        }
        application.addInitializers(t -> {
            if (this.logs != null) {
                for (BaseLogEvent<T> log: this.logs) {
                    log(logger, log);
                    
                }
            }
            this.logs = null;
        });
    }

    /**
     * @param logger
     * @param log
     */
    public abstract void log(L logger, BaseLogEvent<T> log);
    /**
     * @return
     */
    public abstract T getLevelError();

}
