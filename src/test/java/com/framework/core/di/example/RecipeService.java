package com.framework.core.di.example;

import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Service;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    @Inject
    public RecipeService (RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }
}
