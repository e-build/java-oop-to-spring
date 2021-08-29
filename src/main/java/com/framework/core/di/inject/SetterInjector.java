package com.framework.core.di.inject;

import com.framework.core.di.BeanFactory;
import com.framework.core.di.BeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class SetterInjector extends AbstractInjector {

    public SetterInjector(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        Method method = (Method)injectedBean;
        Class<?>[] parameters = method.getParameterTypes();
        if ( parameters.length != 1 )
            throw new IllegalStateException("DI할 메서드 인자는 하나여야 합니다.");
        return parameters[0];
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Method method = (Method)injectedBean;
        try {
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
        }
    }
}
