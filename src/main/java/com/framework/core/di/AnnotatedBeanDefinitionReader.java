package com.framework.core.di;

import com.framework.core.di.annotation.AnnotatedBeanDefinition;
import com.framework.core.di.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry){
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void register(Class<?>... annotatedClasses){
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }

    }

    public void registerBean(Class<?> annotatedClass){
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for ( Method beanMethod : beanMethods ){
            log.debug("@Bean method : {}", beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }

    }


}
