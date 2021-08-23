package com.framework.core.di;

import com.framework.core.new_mvc.annotation.Controller;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanFactory {

    private final Set<Class<?>> preInstantiateBeans;
    Map<Class<?>, Object> beans = Maps.newHashMap();;

    public BeanFactory(Set<Class<?>> preInstantiateBeans){
        this.preInstantiateBeans = preInstantiateBeans;
    }

    public void initialize(){
        for ( Class<?> clazz : preInstantiateBeans){
            if (beans.get(clazz) == null){
                beans.put(clazz, instantiateClass(clazz));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType){
        return (T) beans.get(requiredType);
    }

    /**
     * <pre>
     *     @Inject 어노테이션이 설정되어있는 생성자에 대한 인스턴스 생성한다.
     * </pre>
     * @param clazz
     * @return Object
     */
    private Object instantiateClass(Class<?> clazz) {
        // 이미 생성된 빈이면 리턴
        Object bean = beans.get(clazz);
        if (bean != null){
            return bean;
        }

        // 의존관계 주입이 필요한 생성자 확인
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if( injectedConstructor == null ){
            return BeanUtils.instantiateClass(clazz);
        }

        log.debug("Constructor : {}", injectedConstructor);
        return instantiateConstructor(injectedConstructor);
    }

    /**
     * 인자가 있는 생성자에 대한 인스턴스를 생성한다.
     * @param constructor
     * @return
     */
    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes(); // 의존관계 주입이 필요한 생성자의 인자 타입
        List<Object> args = Lists.newArrayList(); // 인스턴스화된 인자들을 담을 리스트
        for ( Class<?> clazz :  parameterTypes ){
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans); // 주입할 인자의 구체화된 클래스 탐색
            if ( !preInstantiateBeans.contains(concreteClazz) )
                throw new IllegalStateException(clazz + "는 Bean이 아닙니다.");

            Object bean = beans.get(concreteClazz);
            if (bean == null) // beans에 있는 지 확인
                bean = instantiateClass(concreteClazz); // 없으면 인스턴스생성
            args.add(bean); // 이미 생성된 bean이 있으면 인자 리스트에 추가
        }
        return BeanUtils.instantiateClass(constructor, args.toArray()); // 인스턴스화된 인자들를 생성자에 주입하여 인스턴스 생성
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
