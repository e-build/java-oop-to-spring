package com.framework.core.di;

import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class BeanDefinition {

    private final Class<?> beanClazz;
    private final Constructor<?> injectConstructor;
    private final Set<Field> injectFields;

    public BeanDefinition(Class<?> beanClazz){
        this.beanClazz = beanClazz;
        this.injectConstructor = getInjectConstructor(beanClazz);
        this.injectFields = getInjectFields(beanClazz);
    }

    private Constructor<?> getInjectConstructor(Class<?> clazz){
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }

    private Set<Field> getInjectFields(Class<?> clazz){
        if (this.injectConstructor != null)
            return Sets.newHashSet();

        Set<Field> injectFields = Sets.newHashSet();
        Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for ( Field field : fields ){
            if ( injectProperties.contains(field.getType()) )
                injectFields.add(field);
        }

        return injectFields;
    }

    private Set<Class<?>> getInjectPropertiesType(Class<?> clazz){
        Set<Class<?>> injectProperties = Sets.newHashSet();
        Set<Method> injectMethods = BeanFactoryUtils.getInjectedMethods(clazz);

        if (injectMethods == null)
            return injectProperties;

        for ( Method method : injectMethods ){
            Class<?>[] pTypes = method.getParameterTypes();
            if ( pTypes.length != 1 )
                throw new IllegalStateException("DI할 메서드 인자는 하나여야 합니다.");

            injectProperties.add(pTypes[0]);
        }
        return injectProperties;
    }

    public Constructor<?> getInjectConstructor(){
        return this.injectConstructor;
    }

    public Set<Field> getInjectFields(){
        return this.injectFields;
    }

    public Class<?> getBeanClass(){
        return this.beanClazz;
    }

    public InjectType getResolvedInjectMode(){
        if( injectConstructor != null)
            return InjectType.INJECT_CONSTRUCTOR;

        if( !injectFields.isEmpty() )
            return InjectType.INJECT_FIELD;

        return InjectType.INJECT_NO;
    }

}
