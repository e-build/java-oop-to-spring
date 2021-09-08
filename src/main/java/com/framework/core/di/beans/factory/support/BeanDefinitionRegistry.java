package com.framework.core.di.beans.factory.support;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(Class<?> clazz, DefaultBeanDefinition beanDefinition);
}
