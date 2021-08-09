package com.bussiness;

import com.bussiness.recipe.domain.Recipe;
import com.bussiness.user.domain.User;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    public static final Map<Integer, User> USER = Maps.newHashMap();
    public static final Map<Integer, Recipe> RECIPE = Maps.newHashMap();

    static {
        USER.put(1, new User(1, "1", "1", "1"));
        USER.put(2, new User(2, "2", "2", "2"));
        USER.put(3, new User(3, "3", "3", "3"));
    }

    public static User readUserById(Integer userId){
        for ( int key : USER.keySet() ){
            if (USER.get(key).getId() == userId)
                return USER.get(key);
        }
        return null;
    }

    public static void insertUser(User user){
        USER.put(USER.size()+1,user);
    }

    public static List<Recipe> readRecipeByUser(User user){
        List<Recipe> recipeList = new ArrayList<Recipe>();
        for ( int key : RECIPE.keySet() ){
            if (RECIPE.get(key).getCreatedBy() == user.getId())
                recipeList.add(RECIPE.get(key));
        }
        return recipeList;
    }

    public static void insertRecipe(Recipe recipe){
        RECIPE.put(RECIPE.size()+1,recipe);
    }
}
