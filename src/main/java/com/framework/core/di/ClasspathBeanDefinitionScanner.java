package com.framework.core.di;

import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.Repository;
import com.framework.core.new_mvc.annotation.Service;
import com.google.common.collect.Sets;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClasspathBeanDefinitionScanner {

    private final BeanDefinitionRegistry beanDefinitionRegistry;
    public ClasspathBeanDefinitionScanner( BeanDefinitionRegistry beanDefinitionRegistry){
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @SuppressWarnings("unchecked")
    public void doScan(Object... basePackage){
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Controller.class, Service.class, Repository.class);
        for( Class<?> clazz : beanClasses ){
            beanDefinitionRegistry.registerBeanDefinition(clazz, new BeanDefinition(clazz));
        }
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations){
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for ( Class<? extends Annotation> annotation : annotations ){
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }


}
