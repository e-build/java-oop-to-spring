package com.framework.core.di;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);
}
