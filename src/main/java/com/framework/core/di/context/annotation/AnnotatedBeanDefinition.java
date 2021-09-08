package com.framework.core.di.context.annotation;

import com.framework.core.di.beans.factory.support.DefaultBeanDefinition;

import java.lang.reflect.Method;

public class AnnotatedBeanDefinition extends DefaultBeanDefinition {

    private final Method method;

    /**
     * 인자로 전달되는 클래스를 통해, 리플렉션으로 생성자, 수정자, 필드 주입할 요소들 초기화
     *
     * @param beanClazz
     */
    public AnnotatedBeanDefinition(Class<?> beanClazz, Method method) {
        super(beanClazz);
        this.method = method;
    }

    public Method getMethod(){
        return this.method;
    }
}
