package com.framework.core.di.inject;

import com.framework.core.di.BeanFactory;
import com.google.common.collect.Sets;

import java.util.Set;

public class ConstructorInjector extends AbstractInjector {

    public ConstructorInjector(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return Sets.newHashSet();
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return null;
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {

    }
}
