package com.business.recipe.controller;

import com.business.recipe.dao.RecipeDao;
import com.business.recipe.domain.Recipe;
import com.business.recipe.service.RecipeService;
import com.business.user.domain.User;
import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.RequestMapping;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.constants.HttpMethod;
import com.framework.utils.DateUtils;
import com.framework.utils.JsonUtils;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

@Controller
public class RecipeController {

    RecipeDao recipeDao = new RecipeDao();
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

    @RequestMapping(method = HttpMethod.GET, value = "/recipe/detail")
    public void detailPage(HttpRequest request, HttpResponse response){
        response.forward("/recipe/detail");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/recipe/form")
    public void formPage(HttpRequest request, HttpResponse response){
        response.forward("/recipe/form");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/api/recipe/list")
    public void list(HttpRequest request, HttpResponse response){
        response.responseBody(recipeDao.selectRecipeList());
    }

    @RequestMapping(method = HttpMethod.GET, value = "/api/recipe/detail")
    public void detail(HttpRequest request, HttpResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        response.responseBody(recipeDao.selectRecipeById(id));
    }

    @RequestMapping(method = HttpMethod.POST, value = "/api/recipe/register")
    public void register(HttpRequest request, HttpResponse response){
        Map<String, String> params = JsonUtils.deserialize(request.getRequestBody(), Map.class);
        User user = (User)request.getSession().getAttribute("user");

        Recipe recipe = new Recipe();
        recipe.setName(params.get("name"));
        recipe.setContents(params.get("contents"));
        recipe.setCategory(params.get("category"));
        recipe.setCreatedAt(DateUtils.currentDateTime());
//        recipe.setCreatedBy(user.getId());
        recipe.setUpdateAt(DateUtils.currentDateTime());
//        recipe.setUpdateBy(user.getId());

        Map<String, Object> bodyMap = Maps.newHashMap();
        bodyMap.put("result", recipeDao.insertOne(recipe));
        response.responseBody(bodyMap);
    }


}
