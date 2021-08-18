package com.business.recipe.controller;

import com.business.recipe.dao.RecipeDao;
import com.business.recipe.domain.Recipe;
import com.business.user.domain.User;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.DateUtils;
import com.framework.utils.JsonUtils;
import com.google.common.collect.Maps;

import java.util.Map;

public class RecipeRegisterApiController implements Controller {

    private final RecipeDao recipeDao = new RecipeDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        Map<String, String> params = JsonUtils.deserialize(request.getRequestBody(), Map.class);
        User user = (User)request.getSession().getAttribute("user");

        Recipe recipe = new Recipe();
        recipe.setName(params.get("name"));
        recipe.setContents(params.get("contents"));
        recipe.setCategory(params.get("category"));
        recipe.setCreatedAt(DateUtils.currentDateTime());
//        recipe.setCreatedBy(user.getId());
        recipe.setUpdateAt(DateUtils.currentDateTime());
//        recipe.setUpdateBy(user.getId());

        Map<String, Object> bodyMap = Maps.newHashMap();
        bodyMap.put("result", recipeDao.insertOne(recipe));
        response.responseBody(bodyMap);
    }
}
