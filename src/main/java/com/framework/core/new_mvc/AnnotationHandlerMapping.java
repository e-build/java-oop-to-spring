package com.framework.core.new_mvc;

import com.framework.core.di.BeanFactory;
import com.framework.core.di.BeanScanner;
import com.framework.core.new_mvc.annotation.RequestMapping;
import com.framework.http.HandlerKey;
import com.framework.http.HttpRequest;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final String scanPackage;
    private static final Map<HandlerKey, HandlerExecution> handlers = Maps.newHashMap();

    public AnnotationHandlerMapping(String scanPackage){
        this.scanPackage = scanPackage;
    }

    private void addHandler(Object object, Method[] methods){
        for ( Method method : methods ){
            if ( method.isAnnotationPresent(RequestMapping.class) )
                addHandler(object, method);
        }
    }

    private void addHandler(Object object, Method method){
        RequestMapping mappedHandler = method.getAnnotation(RequestMapping.class);
        handlers.put(
                HandlerKey.of(mappedHandler.method(), mappedHandler.value()),
                HandlerExecution.of(method, object)
        );
    }

    public void initialize(){
        BeanScanner beanScanner = new BeanScanner(scanPackage);
        BeanFactory beanFactory = new BeanFactory(beanScanner.scan());
        beanFactory.initialize();
        Map<Class<?>, Object> controllers = beanFactory.getControllers();
        for( Class<?> clazz : controllers.keySet() ){
            Method[] methods = clazz.getDeclaredMethods();
            addHandler(controllers.get(clazz), methods);
        }
    }

    @Override
    public HandlerExecution getHandler(HttpRequest request){
        return handlers.get(HandlerKey.of(request.getMethod(), request.getPath()));
    }

}
