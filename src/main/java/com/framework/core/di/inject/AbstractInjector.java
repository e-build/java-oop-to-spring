package com.framework.core.di.inject;

import com.framework.core.di.BeanFactory;
import com.framework.core.di.BeanFactoryUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

@Slf4j
public abstract class AbstractInjector implements Injector{

    private final BeanFactory beanFactory;

    public AbstractInjector(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    abstract Set<?> getInjectedBeans(Class<?> clazz);

    abstract Class<?> getBeanClass(Object injectedBean);

    abstract void inject(Object injectedBean, Object bean, BeanFactory beanFactory);

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        Set<?> injectedBeans = getInjectedBeans(clazz);
        if ( injectedBeans == null)
            return;
        for ( Object injectedBean : injectedBeans ){
            Class<?> beanClass = getBeanClass(injectedBean);
            inject(injectedBean, instantiateClass(beanClass), beanFactory);
        }
    }

    /**
     * <pre>
     *     @Inject 어노테이션이 설정되어있는 생성자에 대한 인스턴스 생성한다.
     * </pre>
     * @param clazz
     * @return Object
     */
    private Object instantiateClass(Class<?> clazz) {
        Class<?> concreteClass = findBeanClass(clazz, beanFactory.getPreInstantiateBeans());
        // 이미 생성된 빈이면 리턴
        Object bean = beanFactory.getBean(concreteClass);
        if (bean != null){
            return bean;
        }

        // 의존관계 주입이 필요한 생성자 확인
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
        if( injectedConstructor == null ){
            bean = BeanUtils.instantiateClass(concreteClass);
            beanFactory.addBean(concreteClass, bean);
            return bean;
        }

        log.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.addBean(concreteClass, bean);
        return bean;
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
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstantiateBeans()); // 주입할 인자의 구체화된 클래스 탐색
            if ( !beanFactory.getPreInstantiateBeans().contains(concreteClazz) )
                throw new IllegalStateException(clazz + "는 Bean이 아닙니다.");

            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) // beans에 있는 지 확인
                bean = instantiateClass(concreteClazz); // 없으면 인스턴스생성
            args.add(bean); // 이미 생성된 bean이 있으면 인자 리스트에 추가
        }
        return BeanUtils.instantiateClass(constructor, args.toArray()); // 인스턴스화된 인자들를 생성자에 주입하여 인스턴스 생성
    }

    private Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstantiateBeans){
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        if (!preInstantiateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 Bean이 아니다.");
        }
        return concreteClazz;

    }

}
