package com.framework.core.mvc;

import com.business.recipe.controller.*;
import com.business.user.controller.*;
import com.framework.core.mvc.Controller;
import com.framework.core.new_mvc.HandlerMapping;
import com.framework.http.HandlerKey;
import com.framework.http.HttpRequest;
import com.framework.http.constants.HttpMethod;
import com.google.common.collect.Maps;

import java.util.Map;

public class LegacyHandlerMapping implements HandlerMapping {

    private static final Map<HandlerKey, Controller> controllers = Maps.newHashMap();

    public void initialize(){
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/login"), new UserLoginPageController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/logout"), new UserLogoutController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/user/register"), new UserRegistPageController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/list"), new RecipeListPageController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/detail"), new RecipeDetailPageController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/recipe/form"), new RecipeFormPageController());

        // API
//        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/register"), new UserRegistApiController());
//        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/user/login"), new UserLoginApiController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/user/login/check"), new UserLoginCheckApiController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/list"), new RecipeListApiController());
//        controllers.put(HandlerKey.of(HttpMethod.GET, "/api/recipe/detail"), new RecipeDetailApiController());
//        controllers.put(HandlerKey.of(HttpMethod.POST, "/api/recipe/register"), new RecipeRegisterApiController());
    }

    @Override
    public Object getHandler(HttpRequest request) {
        return controllers.get(HandlerKey.of(request.getMethod(), request.getPath()));
    }
}
