package com.framework.http;

import com.bussiness.home.HomePageController;
import com.bussiness.recipe.controller.RecipeDetailApiController;
import com.bussiness.recipe.controller.RecipeDetailPageController;
import com.bussiness.recipe.controller.RecipeListApiController;
import com.bussiness.recipe.controller.RecipeListPageController;
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

        // API
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/register"), new UserRegistApiController());
        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/login"), new UserLoginApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/user/logout"), new UserLogoutApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/list"), new RecipeListApiController());
        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/detail"), new RecipeDetailApiController());
    }

    public static Controller getController(HandlerKey handlerKey) {
        return controllers.get(handlerKey);
    }
}
