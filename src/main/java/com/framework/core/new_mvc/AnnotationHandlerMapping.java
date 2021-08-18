package com.framework.core.new_mvc;

import com.framework.core.di.ControllerScanner;
import com.framework.http.HandlerKey;
import com.framework.http.HttpRequest;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping{

    private final String scanPackage;
    private static final Map<HandlerKey, HandlerExecution> handlers = Maps.newHashMap();

    public AnnotationHandlerMapping(String scanPackage){
        this.scanPackage = scanPackage;
    }

    private void addHandler(Class<?> controller, Method[] methods){
        for ( Method method : methods ){
            if ( method.isAnnotationPresent(RequestMapping.class) )
                addHandler(controller, method);
        }
    }

    private void addHandler(Class<?> controller, Method method){
        RequestMapping mappedHandler = method.getAnnotation(RequestMapping.class);
        try {
            handlers.put(
                    HandlerKey.of(mappedHandler.method(), mappedHandler.value()),
                    HandlerExecution.of(method, controller.newInstance())
            );
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        Set<Class<?>> controllerClasses = ControllerScanner.scan(scanPackage);
        for( Class<?> controller : controllerClasses ){
            Method[] methods = controller.getDeclaredMethods();
            addHandler(controller, methods);
        }
    }

    @Override
    public HandlerExecution getHandler(HttpRequest request){
        return handlers.get(HandlerKey.of(request.getMethod(), request.getPath()));
    }

}
