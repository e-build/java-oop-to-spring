package com.bussiness;

import com.bussiness.user.domain.User;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    public static final Map<Integer, User> USER = Maps.newHashMap();
//    public static final Map<Integer, Recipe> RECIPE = Maps.newHashMap();

    static {
        USER.put(1, new User(1, "1@gmail.com", "1234qwer", "11111"));
    }

    public static User readUserByUsername(String username){
        for ( int key : USER.keySet() ){
            if (StringUtils.equals(username, USER.get(key).getUsername()))
                return USER.get(key);
        }
        return null;
    }

    public static User readUserById(Integer userId){
        for ( int key : USER.keySet() ){
            if (USER.get(key).getId() == userId)
                return USER.get(key);
        }
        return null;
    }

    public static void insertUser(User user){
        USER.put(user.getId(), user);
    }

//    public static List<Recipe> readRecipeByUser(User user){
//        List<Recipe> recipeList = new ArrayList<Recipe>();
//        for ( int key : RECIPE.keySet() ){
//            if (StringUtils.equals(RECIPE.get(key).getCreatedBy(), user.getUsername()))
//                recipeList.add(RECIPE.get(key));
//        }
//        return recipeList;
//    }
//
//    public static void insertRecipe(Recipe recipe){
//        RECIPE.put(RECIPE.size()+1,recipe);
//    }
}
