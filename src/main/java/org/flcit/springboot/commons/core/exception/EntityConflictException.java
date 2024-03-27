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

package org.flcit.springboot.commons.core.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.flcit.commons.core.util.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@SuppressWarnings("java:S110")
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityConflictException extends ConflictException {

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_ENTITY_ALREADY_EXISTS = "ENTITY %s WITH ID %s ALREADY EXISTS";

    /**
     * @param table
     * @param id
     */
    public EntityConflictException(String table, Object id) {
        super(String.format(MESSAGE_ENTITY_ALREADY_EXISTS, table, StringUtils.convertOrNull(id)));
    }

    /**
     * @param cause
     */
    public EntityConflictException(Throwable cause) {
        super(cause);
    }

}
