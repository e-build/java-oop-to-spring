package com.framework.core.new_mvc.adapter;

import com.framework.core.new_mvc.HandlerExecution;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class HandlerExecutionAdapter implements HandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public Object handle(HttpRequest request, HttpResponse response, Object handler) {
        ((HandlerExecution)handler).handle(request, response);
        return null;
    }
}
