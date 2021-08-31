package com.framework.core.di;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanFactory implements BeanDefinitionRegistry{

    private final Map<Class<?>, Object> beans = Maps.newHashMap();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    public void initialize(){
        for ( Class<?> clazz : getBeanClasses() ){
            log.debug("bean name : {}", clazz.getTypeName());
            getBean(clazz);
        }
    }

    private Object inject(BeanDefinition beanDefinition){
        if ( beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO )
            return BeanUtils.instantiateClass(beanDefinition.getBeanClass());
        if ( beanDefinition.getResolvedInjectMode() == InjectType.INJECT_CONSTRUCTOR )
            return injectConstructor(beanDefinition);
        return injectFields(beanDefinition);
    }

    private Object injectConstructor( BeanDefinition beanDefinition ) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        List<Object> args = Lists.newArrayList();

        for ( Class<?> pTypeClazz : constructor.getParameterTypes() )
            args.add(getBean(pTypeClazz));

        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    private Object injectFields( BeanDefinition beanDefinition ) {
        Object bean = BeanUtils.instantiateClass(beanDefinition.getBeanClass());
        Set<Field> fields = beanDefinition.getInjectFields();

        for ( Field field : fields ){
            injectField(bean, field);
            log.debug("field inject : {}, {}", bean, field);
        }

        return bean;
    }

    private void injectField(Object bean, Field field){
        try {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }

    private void addBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition){
        this.beanDefinitions.put(clazz, beanDefinition);
    }

    private Class<?> findConcreteClass(Class<?> clazz){
        Set<Class<?>> beanClasses = getBeanClasses();
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanClasses);

        if ( !beanClasses.contains(concreteClass) )
            throw new IllegalStateException(clazz + "는 Bean이 아닙니다.");

        return concreteClass;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType){
        Object bean = beans.get(requiredType);
        if ( bean != null )
            return (T)bean;
        Class<?> concreteClass = findConcreteClass(requiredType);
        BeanDefinition beanDefinition = new BeanDefinition(concreteClass);
        bean = inject(beanDefinition);
        addBean(concreteClass, bean);
        return (T) bean;
    }

    public void addBean(Class<?> requiredType, Object object){
        beans.put(requiredType, object);
    }

    public Set<Class<?>> getBeanClasses(){
        return this.beanDefinitions.keySet();
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        addBeanDefinition(clazz, beanDefinition);
    }
}
