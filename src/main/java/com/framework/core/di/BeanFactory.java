package com.framework.core.di;

import com.framework.core.di.annotation.Inject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {

    private final Set<Class<?>> preInstantiateClazz;
    Map<Class<?>, Object> beans = Maps.newHashMap();;

    public <T> T getBean(Class<T> clazz){
        return null;
    }

    public BeanFactory(Set<Class<?>> preInstantiateClazz){
        this.preInstantiateClazz = preInstantiateClazz;
    }

    public void initialize(){

    }

    /**
     * <pre>
     *     @Inject 어노테이션이 설정되어있는 생성자에 대한 인스턴스 생성한다.
     * </pre>
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object instantiateClass(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors){
            if (constructor.isAnnotationPresent(Inject.class)){
                if (ArrayUtils.isEmpty(constructor.getParameterTypes()))
                    return BeanUtils.instantiateClass(clazz);
                return instantiateConstructor(constructor);
            }
        }
        return null;
    }

    /**
     * 인자가 있는 생성자에 대한 인스턴스를 생성한다.
     * @param constructor
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object instantiateConstructor(Constructor<?> constructor) throws InstantiationException, IllegalAccessException {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for ( Class<?> clazz :  parameterTypes ){
            Object bean = instantiateClass(clazz);
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
