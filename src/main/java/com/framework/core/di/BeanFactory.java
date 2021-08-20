package com.framework.core.di;

import java.util.Set;

public class BeanFactory {

    Set<Class<?>> preInstantiateClazz;

    public <T> T getBean(Class<T> clazz){
        return null;
    }

    public BeanFactory(Set<Class<?>> preInstantiateClazz){
        this.preInstantiateClazz = preInstantiateClazz;
    }

    public void initialize(){

    }
}
