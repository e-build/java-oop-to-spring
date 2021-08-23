package com.framework.core.di;

import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.Repository;
import com.framework.core.new_mvc.annotation.Service;
import com.google.common.collect.Sets;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class BeanScanner {

    private Reflections reflections;

    public BeanScanner(String scanPackage){
        reflections = new Reflections(scanPackage);
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> scan(){
        return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
    }

    @SuppressWarnings("unchecked")
    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations){
        Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for ( Class<? extends Annotation> annotation : annotations ){
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }


}
