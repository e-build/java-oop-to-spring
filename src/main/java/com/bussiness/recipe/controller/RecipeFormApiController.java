package com.bussiness.recipe.controller;

import com.bussiness.recipe.dao.RecipeDao;
import com.bussiness.recipe.domain.Recipe;
import com.bussiness.user.domain.User;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.JsonUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class RecipeFormApiController implements Controller {

    private final RecipeDao recipeDao = new RecipeDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        Map<String, String> params = JsonUtils.deserialize(request.getRequestBody(), Map.class);
        User user = (User)request.getSession().getAttribute("user");

        Recipe recipe = new Recipe();
        recipe.setName(params.get("name"));
        recipe.setContents(params.get("contents"));
        recipe.setCategory(params.get("category"));
        recipe.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        recipe.setCreatedBy(user.getId());
        recipe.setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
        recipe.setUpdateBy(user.getId());

        String result = "false";
        if (recipeDao.insertOne(recipe))
            result = "true";
        response.responseBody("{'result':'" + result + "'}");
    }
}
