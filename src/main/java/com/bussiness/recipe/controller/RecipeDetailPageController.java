package com.bussiness.recipe.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class RecipeDetailPageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) { response.forward("/recipe/detail"); }
}