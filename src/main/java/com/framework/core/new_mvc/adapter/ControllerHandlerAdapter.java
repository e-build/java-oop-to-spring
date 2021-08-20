package com.framework.core.new_mvc.adapter;

import com.framework.core.mvc.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class ControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof Controller;
    }

    @Override
    public Object handle(HttpRequest request, HttpResponse response, Object handler) {
        ((Controller)handler).service(request, response);
        return null;
    }
}
