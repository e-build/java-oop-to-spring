package com.business.recipe.controller;

import com.business.recipe.dao.RecipeDao;
import com.framework.core.mvc.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class RecipeListApiController implements Controller {

    private final RecipeDao recipeDao = new RecipeDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.responseBody(recipeDao.selectRecipeList());
    }
}
