package com.business.recipe.controller;

import com.business.recipe.dao.RecipeDao;
import com.framework.core.mvc.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class RecipeDetailApiController implements Controller {

    private final RecipeDao recipeDao = new RecipeDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        response.responseBody(recipeDao.selectRecipeById(id));
    }
}
