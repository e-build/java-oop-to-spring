package com.bussiness.user.controller;

import com.bussiness.Database;
import com.bussiness.user.domain.User;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.QueryStringUtils;

import java.util.Map;

public class UserRegistApiController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        Map<String, String>  requestBodyMap = QueryStringUtils.toMap(request.getRequestBody());
        String username = requestBodyMap.get("username").trim();
        String password = requestBodyMap.get("password").trim();
        String nickname = requestBodyMap.get("nickname").trim();
        Database.insertUser(new User(Database.USER.size()+1, username, password, nickname));
        response.sendRedirect("/");
    }
}
