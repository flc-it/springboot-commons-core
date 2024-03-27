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

import org.flcit.commons.core.util.ObjectUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class EnvironmentUtils {

    private static final String DEV = "dev";
    private static final String DOC = "doc";
    private static final String PRD = "prd";

    private EnvironmentUtils() { }

    /**
     * @param environment
     * @return
     */
    public static boolean isProfileDoc(final Environment environment) {
        return isProfile(environment, DOC);
    }

    /**
     * @param environment
     * @return
     */
    public static boolean isProfileDev(final Environment environment) {
        return isProfile(environment, DEV);
    }

    /**
     * @param environment
     * @param profile
     * @return
     */
    public static boolean isProfile(final Environment environment, final String profile) {
        return environment.acceptsProfiles(Profiles.of(profile));
    }

    /**
     * @param environment
     * @return
     */
    public static boolean isEnvironmentPrd(final Environment environment) {
        return PRD.equalsIgnoreCase(getEnvironment(environment));
    }

    /**
     * @param environment
     * @return
     */
    public static String getEnvironment(final Environment environment) {
        return ObjectUtils.getOrDefault(
                environment.getProperty("server.exec.environment"),
                () -> environment.getProperty("info.server.environment"),
                () -> environment.getRequiredProperty("info.app.custom.environment")
        );
    }

}
