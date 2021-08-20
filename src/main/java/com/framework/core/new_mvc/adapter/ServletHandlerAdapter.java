package com.framework.core.new_mvc.adapter;

import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

public class ServletHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean support(Object handler) {
        return handler instanceof Servlet;
    }

    @Override
    public Object handle(HttpRequest request, HttpResponse response, Object handler) {
//        ((Servlet)handler).service(requeset, response);
        return null;
    }
}
