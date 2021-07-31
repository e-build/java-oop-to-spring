package com.framework.http;

import com.bussiness.home.controller.HomePageController;
import com.bussiness.user.controller.*;
import com.google.common.collect.Maps;

import java.util.Map;

public class RequestMapping {

    private static final Map<HandlerKey, Controller> controllerMap = Maps.newHashMap();

    static {
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/home"), new HomePageController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/user/login"), new UserLoginPageController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.POST, "/user/login"), new UserLoginApiController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/user/list"), new UserListPageController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/user/detail"), new UserDetailPageController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/api/user/list"), new UserListApiController());
        controllerMap.put(HandlerKey.of(HttpConstants.Method.GET, "/api/user/detail"), new UserDetailApiController());
    }

    public static Controller getController(HandlerKey handlerKey){
        return controllerMap.get(handlerKey);
    }

}

