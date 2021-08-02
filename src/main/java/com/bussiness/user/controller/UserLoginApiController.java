package com.bussiness.user.controller;

import com.bussiness.Database;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.QueryStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class UserLoginApiController implements Controller {

    Logger log = LoggerFactory.getLogger(UserLoginApiController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> queryParams = QueryStringUtils.toMap(request.getRequestBody());
        String username =  queryParams.get("username");
        String password =  queryParams.get("password");

        for (String pk : Database.User.keySet()){
            if ( StringUtils.equals(username, Database.User.get(pk).getUsername()) ) {
                if ( StringUtils.equals(password, Database.User.get(pk).getPassword()) ) {
                    response.addHeader("Set-Cookie","login=true");
                    response.sendRedirect("/home");
                } else {
                    response.addHeader("Set-Cookie","login=false");
                    response.sendRedirect("/user/login");
                }
                break;
            }
        }

    }
}
