package com.framework.core.di.inject;

import com.framework.core.di.BeanFactory;
import com.framework.core.di.BeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class FieldInjector extends AbstractInjector {

    public FieldInjector(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        return ((Field)injectedBean).getType();
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Field field = (Field)injectedBean;
        try{
            field.setAccessible(true);
            field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
        }
    }
}
