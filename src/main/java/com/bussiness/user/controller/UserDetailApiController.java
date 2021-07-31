package com.bussiness.user.controller;

import com.bussiness.Database;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserDetailApiController implements Controller {
    Logger log = LoggerFactory.getLogger(UserDetailApiController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        log.info("UserDetailApiController 실행, {}", request.getParameter("id"));

        response.responseBody(JsonUtils.serialize(Database.User.get(request.getParameter("id"))));
    }

}
