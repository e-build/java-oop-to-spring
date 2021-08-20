package com.framework.core.new_mvc.adapter;

import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public interface HandlerAdapter {

    boolean support(Object handler);

    Object handle(HttpRequest request, HttpResponse response, Object handler);
}
