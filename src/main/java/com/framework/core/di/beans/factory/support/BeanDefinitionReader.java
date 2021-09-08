package com.framework.core.di.beans.factory.support;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(Class<?>... annotatedClasses);
}
