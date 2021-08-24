package com.business.recipe.service;

import com.business.recipe.dao.RecipeDao;
import com.business.recipe.domain.Recipe;
import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Service;
import com.framework.utils.DateUtils;

import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeDao recipeDao;

    @Inject
    public RecipeService(RecipeDao recipeDao){
        this.recipeDao = recipeDao;
    }

    public int register(Map<String, String> params){
        Recipe recipe = new Recipe();
        recipe.setName(params.get("name"));
        recipe.setContents(params.get("contents"));
        recipe.setCategory(params.get("category"));
        recipe.setCreatedAt(DateUtils.currentDateTime());
//        recipe.setCreatedBy(user.getId());
        recipe.setUpdateAt(DateUtils.currentDateTime());
//        recipe.setUpdateBy(user.getId());
        return recipeDao.insertOne(recipe);
    }

    public List<Recipe> recipeList(){
        return recipeDao.selectRecipeList();
    }

    public Recipe recipeOne(int id){
        return recipeDao.selectRecipeById(id);
    }



}
