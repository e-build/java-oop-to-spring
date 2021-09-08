package com.framework.core.di;

import com.framework.core.di.annotation.Inject;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import static org.reflections.ReflectionUtils.*;

@Slf4j
public class BeanFactoryUtils {

    /**
     * 인자로 전달하는 클래스의 생성자 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 필드를 반환
     *
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked" })
    public static Set<Field> getInjectedFields(Class<?> clazz) {
        Set<Field> injectedFields = getAllFields(clazz, withAnnotation(Inject.class));
        if (injectedFields.isEmpty())
            return null;
        return injectedFields;
    }

    /**
     * @Inject 애노테이션이 설정되어 있는 메서드를 반환
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked" })
    public static Set<Method> getInjectedMethods(Class<?> clazz) {
        Set<Method> injectedMethods = getAllMethods(clazz, withAnnotation(Inject.class));
        if (injectedMethods.isEmpty())
            return null;
        return injectedMethods;
    }

    /**
     * 인자로 전달되는 클래스의 구현 클래스.
     * 만약 인자로 전달되는 Class가 인터페이스가 아니면 전달되는 인자가 구현 클래스,
     * 인터페이스인 경우 BeanFactory가 관리하는 모든 클래스 중에 인터페이스를 구현하는 클래스를 찾아 반환
     *
     * @param injectedClazz
     * @param preInstantiateBeans
     * @return
     */
    public static Optional<Class<?>> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstantiateBeans) {
        if (!injectedClazz.isInterface())
            return Optional.of(injectedClazz);

        for ( Class<?> clazz : preInstantiateBeans ) {
            Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
            if (interfaces.contains(injectedClazz)) {
                return Optional.of(clazz);
            }
        }
        return Optional.empty();
    }

    public static Optional<Object> invokeMethod(Method method, Object bean, Object[] args) {
        try {
            return Optional.ofNullable(method.invoke(bean, args));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @SuppressWarnings({ "unchecked" })
    public static Set<Method> getBeanMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getAllMethods(clazz, withAnnotation(annotation));
    }
}
