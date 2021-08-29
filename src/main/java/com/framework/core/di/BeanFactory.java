package com.framework.core.di;

import com.framework.core.di.inject.ConstructorInjector;
import com.framework.core.di.inject.FieldInjector;
import com.framework.core.di.inject.Injector;
import com.framework.core.di.inject.SetterInjector;
import com.framework.core.new_mvc.annotation.Controller;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanFactory {

    private final Set<Class<?>> preInstantiateBeans;
    Map<Class<?>, Object> beans = Maps.newHashMap();
    private final List<Injector> injectors;

    public BeanFactory(Set<Class<?>> preInstantiateBeans){
        this.preInstantiateBeans = preInstantiateBeans;
        injectors = Lists.newArrayList(
                new FieldInjector(this),
                new SetterInjector(this),
                new ConstructorInjector(this)
        );
    }

    public void initialize(){
        for ( Class<?> clazz : preInstantiateBeans) {
            if (beans.get(clazz) == null) {
                inject(clazz);
//                beans.put(clazz, instantiateClass(clazz));
            }
        }
    }

    private void inject(Class<?> clazz){
        for ( Injector injector : injectors ){
            injector.inject(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType){
        return (T) beans.get(requiredType);
    }

    public void addBean(Class<?> requiredType, Object object){
        beans.put(requiredType, object);
    }

    public Set<Class<?>> getPreInstantiateBeans(){
        return this.preInstantiateBeans;
    }

    public Map<Class<?>, Object> getControllers(){
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for ( Class<?> clazz : preInstantiateBeans ){
            Annotation controller  = clazz.getAnnotation(Controller.class);
            if ( controller != null )
                controllers.put(clazz, beans.get(clazz));
        }
        return controllers;
    }
}
