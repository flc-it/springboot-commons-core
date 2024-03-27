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

import org.flcit.commons.core.functional.consumer.TriConsumer;
import org.flcit.springboot.commons.core.log.domain.LoggerBaseLogEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.util.CollectionUtils;

/**
 * @param <L>
 * @param <T>
 * @since 
 * @author Florian Lestic
 */
public abstract class MultipleEnvironmentPostProcessorLogger<L, T> {

    private List<LoggerBaseLogEvent<L, T>> logs;
    private TriConsumer<L, String, Throwable> consumerError;
    private TriConsumer<L, T, String> consumerMessage;

    private synchronized void addLogs(L logger, T level, String message, Throwable throwable) {
        if (this.logs == null) {
            this.logs = new ArrayList<>(1);
        }
        this.logs.add(new LoggerBaseLogEvent<>(logger, level, message, throwable));
    }

    /**
     * @return
     */
    public TriConsumer<L, String, Throwable> getConsumerError() {
        if (this.consumerError == null) {
            this.consumerError = this::addError;
        }
        return this.consumerError;
    }

    /**
     * @param logger
     * @param message
     * @param e
     */
    public void addError(L logger, String message, Throwable e) {
        this.addLogs(logger, getLevelError(), message, e);
    }

    /**
     * @return
     */
    public TriConsumer<L, T, String> getConsumerMessage() {
        if (this.consumerMessage == null) {
            this.consumerMessage = this::addMessage;
        }
        return this.consumerMessage;
    }

    /**
     * @param logger
     * @param level
     * @param message
     */
    public void addMessage(L logger, T level, String message) {
        this.addLogs(logger, level, message, null);
    }

    /**
     * @param application
     */
    public void write(final SpringApplication application) {
        this.consumerError = null;
        this.consumerMessage = null;
        if (CollectionUtils.isEmpty(logs)) {
            this.logs = null;
            return;
        }
        application.addInitializers(t -> {
            if (this.logs != null) {
                for (LoggerBaseLogEvent<L, T> log: this.logs) {
                    log(log);
                }
            }
            this.logs = null;
        });
    }

    /**
     * @param log
     */
    public abstract void log(LoggerBaseLogEvent<L, T> log);
    /**
     * @return
     */
    public abstract T getLevelError();

}
