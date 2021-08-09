package com.framework.http;

import com.bussiness.home.HomePageController;
import com.bussiness.user.controller.UserLoginApiController;
import com.bussiness.user.controller.UserLoginPageController;
import com.framework.http.constants.HttpMethod;
import com.google.common.collect.Maps;

import java.util.Map;

public class RequestMapping {

    private static final Map<HandlerKey, Controller> controllers = Maps.newHashMap();

    static {
        controllers.put(HandlerKey.of(HttpMethod.GET, "/"), new HomePageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/login"), new UserLoginPageController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/user/login"), new UserLoginApiController());
    }

    public static Controller getController(HandlerKey handlerKey) {
        return controllers.get(handlerKey);
    }

}
