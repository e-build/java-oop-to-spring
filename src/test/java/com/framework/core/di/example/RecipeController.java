package com.framework.core.di.example;

import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.RequestMapping;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.constants.HttpMethod;

@Controller
public class RecipeController {

    RecipeService recipeService;

    @Inject
    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    public RecipeService getRecipeService(){
        return recipeService;
    }

    @RequestMapping(method = HttpMethod.GET, value = "/recipe/list")
    public void listPage(HttpRequest request, HttpResponse response){
        response.forward("/recipe/list");
    }



}
