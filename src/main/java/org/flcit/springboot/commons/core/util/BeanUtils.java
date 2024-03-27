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

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.flcit.commons.core.util.ClassUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * @since 
 * @author Florian Lestic
 */
public final class BeanUtils {

    private BeanUtils() { }

    /**
     * @param applicationContext
     */
    public static void refreshBeans(ApplicationContext applicationContext) {
        refreshBeans(applicationContext, applicationContext.getBeansWithAnnotation(ConfigurationProperties.class));
        refreshBeans(applicationContext, getBeansWithAnnotation(applicationContext, "com.flc.sandbox.commons.core.annotation.RefreshScope"));
        refreshBeans(applicationContext, getBeansWithAnnotation(applicationContext, "org.springframework.cloud.context.scope.refresh.RefreshScope"));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getBeansWithAnnotation(ApplicationContext applicationContext, String className) {
        final Class<? extends Annotation> clazz = (Class<? extends Annotation>) ClassUtils.find(className);
        return clazz != null ? applicationContext.getBeansWithAnnotation(clazz) : Collections.emptyMap();
    }

    private static void refreshBeans(ApplicationContext applicationContext, Map<String, Object> beansToRefresh) {
        for (Entry<String, Object> bean : beansToRefresh.entrySet()) {
            try {
                applicationContext.getAutowireCapableBeanFactory().configureBean(bean.getValue(), bean.getKey());
            } catch (Exception e) { /* DO NOTHING */ }
        }
    }

    /**
     * @param source
     * @param target
     */
    public static void copyNonNullProperties(Object source, Object target) {
        if (target != null
                && source != null) {
            org.springframework.beans.BeanUtils.copyProperties(source, target, getNullProperties(source));
        }
    }

    /**
     * @param map
     * @param target
     */
    public static void copyProperties(Map<?, ?> map, Object target) {
        if (!CollectionUtils.isEmpty(map)) {
            final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
            beanWrapper.setPropertyValues(map);
        }
    }

    /**
     * @param source
     * @return
     */
    public static String[] getNullProperties(Object source) {
        final BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(source);
        final Set<String> names = new HashSet<>();
        for (PropertyDescriptor pd : beanWrapper.getPropertyDescriptors()) {
            if (beanWrapper.getPropertyValue(pd.getName()) == null)
                names.add(pd.getName());
        }
        return names.toArray(new String[0]);
    }

    /**
     * @param <T>
     * @param beanFactory
     * @param name
     * @param clazz
     * @param clazzBean
     * @return
     */
    public static <T> T getByNameOrClass(final BeanFactory beanFactory, final String name, final Class<T> clazz, final Class<? extends T> clazzBean) {
        final T bean = getOptionalByNameOrClass(beanFactory, name, clazz, clazzBean);
        if (bean == null) {
            throw new NoSuchBeanDefinitionException(clazz);
        }
        return bean;
    }

    /**
     * @param <T>
     * @param beanFactory
     * @param name
     * @param clazz
     * @param clazzBean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOptionalByNameOrClass(final BeanFactory beanFactory, final String name, final Class<T> clazz, final Class<? extends T> clazzBean) {
        if (StringUtils.hasLength(name)) {
            return clazz != null ? beanFactory.getBean(name, clazz) : (T) beanFactory.getBean(name);
        } else if (clazzBean != null) {
            return beanFactory.getBean(clazzBean);
        }
        return null;
    }

    /**
     * @param <T>
     * @param beanFactory
     * @param type
     * @return
     */
    @SuppressWarnings({ "unchecked", "java:S1168" })
    public static <T> Collection<T> listBeans(final BeanFactory beanFactory, final Class<? extends T> type) {
        if (beanFactory instanceof ListableBeanFactory) {
            return (Collection<T>) ((ListableBeanFactory) beanFactory).getBeansOfType(type).values();
        }
        return null;
    }

    /**
     * @param bean
     * @throws Exception
     */
    public static void initializing(Object bean) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

}
