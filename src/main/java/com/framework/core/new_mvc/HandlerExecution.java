package com.framework.core.new_mvc;

import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Builder
@Getter
public class HandlerExecution {

    private final Method method;
    private final Object controllerInstance;

    public static HandlerExecution of(Method method, Object controllerInstance){
        return HandlerExecution.builder()
                .method(method)
                .controllerInstance(controllerInstance)
                .build();
    }

    public Object handle(HttpRequest request, HttpResponse response){
        try {
            return this.method.invoke(controllerInstance, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
