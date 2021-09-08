package com.framework.core.di.beans.factory.support;

import com.framework.core.di.beans.factory.config.BeanDefinition;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class DefaultBeanDefinition implements BeanDefinition {

    private final Class<?> beanClazz;
    private final Constructor<?> injectConstructor;
    private final Set<Field> injectFields;

    /**
     * 인자로 전달되는 클래스를 통해, 리플렉션으로 생성자, 수정자, 필드 주입할 요소들 초기화
     * @param beanClazz
     */
    public DefaultBeanDefinition(Class<?> beanClazz){
        this.beanClazz = beanClazz;
        this.injectConstructor = getInjectConstructor(beanClazz);
        this.injectFields = getInjectFields(beanClazz);
    }

    /**
     * 생성자 주입을 위한 생성자 탐색
     * @param clazz
     * @return
     */
    private Constructor<?> getInjectConstructor(Class<?> clazz){
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }

    /**
     * 필드 주입, 수정자 주입을 위한 필드 탐색
     * @param clazz
     * @return
     */
    private Set<Field> getInjectFields(Class<?> clazz){
        if (this.injectConstructor != null)
            return Sets.newHashSet();

        Set<Field> injectFields = Sets.newHashSet();

        // Inject 어노테이션 선언된 메서드 추가
        Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for ( Field field : fields ){
            if ( injectProperties.contains(field.getType()) )
                injectFields.add(field);
        }

        // Inject 어노테이션 선언된 필드 추가
        Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
        if (!CollectionUtils.isEmpty(injectedFields))
            injectFields.addAll(injectedFields);

        return injectFields;
    }

    /**
     * Inject 어노테이션 선언된 메서드 탐색
     * @param clazz
     * @return
     */
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

    /**
     * 주입될 생성자 반환
     * @return Constructor<?>
     */
    @Override
    public Constructor<?> getInjectConstructor(){
        return this.injectConstructor;
    }

    /**
     * 주입될 멤버필드 반환
     * @return Set<Field>
     */
    @Override
    public Set<Field> getInjectFields(){
        return this.injectFields;
    }

    /**
     * 빈으로 생성될 클래스 반환
     * @return Class<?>
     */
    @Override
    public Class<?> getBeanClass(){
        return this.beanClazz;
    }

    /**
     * 생성자 주입, 필드 주입, 멤버필드 주입없이 빈 생성가능한 지 확인
     * @return InjectType
     */
    @Override
    public InjectType getResolvedInjectMode(){
        if( injectConstructor != null)
            return InjectType.INJECT_CONSTRUCTOR;

        if( !injectFields.isEmpty() )
            return InjectType.INJECT_FIELD;

        return InjectType.INJECT_NO;
    }

}
