package com.framework.core.di.context;

import java.util.Set;

public interface ApplicationContext {
    public <T> T getBean(Class<?> clazz);
    public Set<Class<?>> getBeanClasses();
}
