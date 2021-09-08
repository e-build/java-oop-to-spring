package com.framework.core.di;

import com.framework.core.di.annotation.AnnotatedBeanDefinition;
import com.framework.core.di.annotation.PostConstruct;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return BeanUtils.instantiateClass(constructor, populateArguments(constructor.getParameterTypes()));
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

    private Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
        AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
        Method method = abd.getMethod();
        Object[] args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method, getBean(method.getDeclaringClass()), args);
    }

    private Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = Lists.newArrayList();
        for (Class<?> param : paramTypes) {
            Object bean = getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(getBean(param));
        }
        return args.toArray();
    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);
        if (initializeMethods.isEmpty())
            return;

        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(initializeMethod, bean, populateArguments(initializeMethod.getParameterTypes()));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz){
        Object bean = beans.get(clazz);
        if ( bean != null )
            return (T) bean;

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if ( beanDefinition instanceof AnnotatedBeanDefinition ){
            Optional<Object> optionalBean = createAnnotatedBean(beanDefinition);
            optionalBean.ifPresent(b -> beans.put(clazz, b));
            initialize(bean, clazz);
            return (T) optionalBean.orElse(null);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        if (!concreteClazz.isPresent())
            return null;

        beanDefinition = beanDefinitions.get(concreteClazz.get());
        bean = inject(beanDefinition);
        beans.put(concreteClazz.get(), bean);
        initialize(bean, concreteClazz.get());
        return (T) bean;
    }

    public void addBean(Class<?> clazz, Object object){
        beans.put(clazz, object);
    }

    public Set<Class<?>> getBeanClasses(){
        return this.beanDefinitions.keySet();
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        addBeanDefinition(clazz, beanDefinition);
    }
}
