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

import java.util.Collection;
import java.util.Map;

import org.flcit.commons.core.util.ClassUtils;
import org.flcit.commons.core.util.ObjectUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class CacheUtils {

    private CacheUtils() { }

    /**
     * @param beanFactory
     * @param managerName
     * @param managerClass
     * @param name
     * @param clazz
     * @return
     */
    public static Cache getOptional(final BeanFactory beanFactory, final String managerName, final Class<? extends CacheManager> managerClass, final String name, final Class<? extends Cache> clazz) {
        final CacheManager manager = BeanUtils.getOptionalByNameOrClass(beanFactory, managerName, CacheManager.class, managerClass);
        return manager != null ? getOptional(manager, name, clazz) : getOptional(beanFactory, name, clazz);
    }

    /**
     * @param manager
     * @param name
     * @param clazz
     * @return
     */
    public static Cache getOptional(final CacheManager manager, final String name, final Class<? extends Cache> clazz) {
        if (manager == null) {
            return null;
        }
        if (StringUtils.hasLength(name)) {
            return manager.getCache(name);
        } else if (clazz != null) {
            return getOptionalIntern(manager, clazz);
        }
        return null;
    }

    /**
     * @param beanFactory
     * @param name
     * @param clazz
     * @return
     */
    public static Cache getOptional(final BeanFactory beanFactory, final String name, final Class<? extends Cache> clazz) {
        Collection<CacheManager> managers = BeanUtils.listBeans(beanFactory, CacheManager.class);
        if (!CollectionUtils.isEmpty(managers)) {
            for (CacheManager manager: managers) {
                final Cache cache = getOptionalIntern(manager, name, clazz);
                if (cache != null) {
                    return cache;
                }
            }
        }
        return BeanUtils.getOptionalByNameOrClass(beanFactory, name, Cache.class, clazz);
    }

    private static Cache getOptionalIntern(final CacheManager manager, final Class<? extends Cache> clazz) {
        return getOptionalIntern(manager, null, clazz);
    }

    private static Cache getOptionalIntern(final CacheManager manager, final String name, final Class<? extends Cache> clazz) {
        if (manager == null
                || (!StringUtils.hasLength(name) && clazz == null)) {
            return null;
        }
        for (String cacheName: manager.getCacheNames()) {
            final Cache cache = manager.getCache(cacheName);
            if (isCache(cache, name, clazz)) {
                return cache;
            }
        }
        return null;
    }

    private static boolean isCache(final Cache cache, final String name, final Class<? extends Cache> clazz) {
        return cache != null
                && (cache.getName().equals(name) || ClassUtils.isClass(cache, clazz));
    }

    /**
     * @param name
     * @param clazz
     * @return
     */
    public static boolean isCacheable(final String name, final Class<? extends Cache> clazz) {
        return StringUtils.hasLength(name)
                || (clazz != null && clazz != Cache.class);
    }

    /**
     * @param <T>
     * @param caches
     * @param method
     * @param beanFactory
     * @param cacheManagerName
     * @param cacheManagerClass
     * @param cacheName
     * @param cacheClass
     * @return
     */
    public static <T> Cache getCache(final Map<T, Cache> caches, final T method, final BeanFactory beanFactory, final String cacheManagerName, final Class<? extends CacheManager> cacheManagerClass, final String cacheName, final Class<? extends Cache> cacheClass) {
        return isCacheable(cacheName, cacheClass) ?
                caches.computeIfAbsent(method, m -> CacheUtils.getOptional(
                        beanFactory,
                        org.flcit.commons.core.util.StringUtils.nullIfEmpty(cacheManagerName),
                        ObjectUtils.nullIfEquals(cacheManagerClass, CacheManager.class),
                        org.flcit.commons.core.util.StringUtils.nullIfEmpty(cacheName),
                        ObjectUtils.nullIfEquals(cacheClass, Cache.class)))
                : null;
    }

}
