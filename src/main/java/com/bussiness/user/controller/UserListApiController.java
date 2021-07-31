package com.bussiness.user.controller;

import com.bussiness.Database;
import com.bussiness.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.JsonUtils;

import java.io.IOException;

public class UserListApiController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String userJsonString = JsonUtils.serialize(Database.User);
        response.responseBody(userJsonString);
    }
}
