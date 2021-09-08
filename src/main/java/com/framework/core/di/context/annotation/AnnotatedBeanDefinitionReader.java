package com.framework.core.di.context.annotation;

import com.framework.core.di.annotation.Bean;
import com.framework.core.di.beans.factory.support.BeanDefinitionReader;
import com.framework.core.di.beans.factory.support.BeanDefinitionRegistry;
import com.framework.core.di.beans.factory.support.BeanFactoryUtils;
import com.framework.core.di.beans.factory.support.DefaultBeanDefinition;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class AnnotatedBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry){
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    private void registerBean(Class<?> annotatedClass){
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new DefaultBeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for ( Method beanMethod : beanMethods ){
            log.debug("@Bean method : {}", beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }
    }

    @Override
    public void loadBeanDefinitions(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }


}
