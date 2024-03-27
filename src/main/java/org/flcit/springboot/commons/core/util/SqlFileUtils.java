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

import java.io.IOException;

import org.flcit.commons.core.util.ArrayUtils;
import org.flcit.commons.core.util.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class SqlFileUtils {

    private SqlFileUtils() { }

    /**
     * @param file
     * @return
     */
    public static String getSqlFile(String file) {
        try {
            return new String(FileCopyUtils.copyToByteArray(new ClassPathResource("sql/" + file).getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * @param sql
     * @param name
     * @param value
     * @return
     */
    public static String replaceInSqlFile(String sql, String name, String value) {
        return sql.replace("${" + name + '}', value);
    }

    /**
     * @param sql
     * @param names
     * @param values
     * @return
     */
    public static String replaceInSqlFile(String sql, String[] names, String... values) {
        if (ArrayUtils.length(names) != ArrayUtils.length(values)) {
            throw new IllegalArgumentException("Size arrays are diferent !");
        }
        for (int i = 0; i < names.length; i++) {
           sql = replaceInSqlFile(sql, names[i], values[i]);
        }
        return sql;
    }

    /**
     * @param value
     * @return
     */
    public static String getValue(String value) {
        return value == null || value.equalsIgnoreCase(StringUtils.NULL) ? StringUtils.NULL : '\'' + value + '\'';
    }

}
