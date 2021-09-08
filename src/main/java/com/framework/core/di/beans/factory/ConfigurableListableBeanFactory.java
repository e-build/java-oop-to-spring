package com.framework.core.di.beans.factory;

public interface ConfigurableListableBeanFactory extends BeanFactory{
    void preInstantiateSingletons();
}
