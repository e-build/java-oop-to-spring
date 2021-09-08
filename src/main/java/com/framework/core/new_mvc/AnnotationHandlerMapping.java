package com.framework.core.new_mvc;

import com.framework.core.di.context.ApplicationContext;
import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.RequestMapping;
import com.framework.http.HandlerKey;
import com.framework.http.HttpRequest;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Map<HandlerKey, HandlerExecution> handlers = Maps.newHashMap();
    private final ApplicationContext applicationContext;

    public AnnotationHandlerMapping(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
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
        log.debug("register handlerExecution : url is {}, method is {}", mappedHandler.value(), method);
    }

    public void initialize(){
        Map<Class<?>, Object> controllers = getControllers(applicationContext);
        for (Class<?> clazz : controllers.keySet())
            addHandler( controllers.get(clazz) , clazz.getDeclaredMethods());
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac){
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for( Class<?> clazz : ac.getBeanClasses() ){
            Annotation annotation = clazz.getAnnotation(Controller.class);
            if ( annotation != null ){
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    @Override
    public HandlerExecution getHandler(HttpRequest request){
        return handlers.get(HandlerKey.of(request.getMethod(), request.getPath()));
    }

}
