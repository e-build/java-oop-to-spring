package com.business.recipe.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class RecipeFormPageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.forward("/recipe/form");
    }
}
