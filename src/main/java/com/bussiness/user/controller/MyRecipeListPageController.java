package com.bussiness.user.controller;

import com.bussiness.Database;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.QueryStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MyRecipeListPageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        Map<String, String> bodyParams = QueryStringUtils.toMap(request.getRequestBody());
        if ( login(bodyParams.get("username"), bodyParams.get("password")) ){
            response.addHeader("Set-Cookie", "login=true");
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/user/login");
        }
    }

    private boolean login(String username, String password){
        for ( Integer key : Database.USER.keySet() ){
            if (StringUtils.equals(Database.USER.get(key).getUsername(), username))
                return StringUtils.equals(Database.USER.get(key).getUsername(), password);
        }
        return false;
    }
}
