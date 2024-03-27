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

import org.flcit.commons.core.exception.BasicRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BasicRuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public NotFoundException() {
        this(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    /**
     * @param message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param code
     * @param message
     */
    public NotFoundException(String code, String message) {
        super(code, message);
    }

}
