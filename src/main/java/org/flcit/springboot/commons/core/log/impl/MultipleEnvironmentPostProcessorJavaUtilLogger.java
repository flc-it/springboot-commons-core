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

package org.flcit.springboot.commons.core.log.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.flcit.springboot.commons.core.log.MultipleEnvironmentPostProcessorLogger;
import org.flcit.springboot.commons.core.log.domain.LoggerBaseLogEvent;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public class MultipleEnvironmentPostProcessorJavaUtilLogger extends MultipleEnvironmentPostProcessorLogger<Logger, Level> {

    @Override
    public void log(LoggerBaseLogEvent<Logger, Level> log) {
        log.getLogger().log(log.getLevel(), log.getMessage(), log.getThrowable());
    }

    @Override
    public Level getLevelError() {
        return Level.SEVERE;
    }

}
