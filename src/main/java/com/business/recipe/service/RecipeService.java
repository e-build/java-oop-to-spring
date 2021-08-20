package com.business.recipe.service;

import com.business.recipe.dao.RecipeDao;
import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Service;
import lombok.Getter;

@Service
public class RecipeService {

    RecipeDao recipeDao;

    @Inject
    public RecipeService (RecipeDao recipeDao){
        this.recipeDao = recipeDao;
    }

    public RecipeDao getRecipeDao() {
        return recipeDao;
    }
}
