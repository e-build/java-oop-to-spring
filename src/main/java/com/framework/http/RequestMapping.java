package com.framework.http;

import com.bussiness.home.HomePageController;
import com.bussiness.recipe.controller.*;
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
        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/list"), new RecipeListPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/detail"), new RecipeDetailPageController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/form"), new RecipeFormPageController());

        // API
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/register"), new UserRegistApiController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/login"), new UserLoginApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/logout"), new UserLogoutController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/user/login/check"), new UserLoginCheckApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/list"), new RecipeListApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/detail"), new RecipeDetailApiController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/recipe/register"), new RecipeRegisterApiController());
    }

    public static Controller getController(HandlerKey handlerKey) {
        return controllers.get(handlerKey);
    }
}
