package com.framework.core.mvc;

import com.framework.core.new_mvc.HandlerMapping;
import com.framework.http.HandlerKey;
import com.framework.http.HttpRequest;
import com.google.common.collect.Maps;

import java.util.Map;

public class LegacyHandlerMapping implements HandlerMapping {

    private static final Map<HandlerKey, Controller> controllers = Maps.newHashMap();

    public void initialize(){

    }

    @Override
    public Object getHandler(HttpRequest request) {
        return controllers.get(HandlerKey.of(request.getMethod(), request.getPath()));
    }
}
