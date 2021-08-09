package com.framework.http;

import com.bussiness.home.HomePageController;
import com.bussiness.user.controller.*;
import com.framework.http.constants.HttpMethod;
import com.google.common.collect.Maps;

import java.util.Map;

public class RequestMapping {

    private static final Map<HandlerKey, Controller> controllers = Maps.newHashMap();

    static {
        // PAGE
        controllers.put(HandlerKey.of(HttpMethod.GET, "/"), new HomePageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/login"), new UserLoginPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/register"), new UserRegistPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/list"), new MyMainPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/detail"), new MyMainPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/my"), new MyMainPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/my/info"), new MyInfoPageController());

        // API
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/register"), new UserRegistApiController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/login"), new UserLoginApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/list"), new MyMainPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/detail"), new MyMainPageController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/my/info"), new MyInfoUpdateApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/my/info"), new MyInfoReadApiController());
    }

    public static Controller getController(HandlerKey handlerKey) {
        return controllers.get(handlerKey);
    }
}
