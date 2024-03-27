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

import org.flcit.springboot.commons.core.log.SimpleEnvironmentPostProcessorLogger;
import org.flcit.springboot.commons.core.log.domain.BaseLogEvent;
import org.flcit.springboot.commons.core.log.util.EnvironmentPostProcessorLogSlf4jUtils;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class SimpleEnvironmentPostProcessorSlf4jLogger extends SimpleEnvironmentPostProcessorLogger<Logger, Level> {

    @Override
    public void log(Logger logger, BaseLogEvent<Level> log) {
        EnvironmentPostProcessorLogSlf4jUtils.log(logger, log.getLevel(), log.getMessage(), log.getThrowable());
    }

    @Override
    public Level getLevelError() {
        return Level.ERROR;
    }

}
