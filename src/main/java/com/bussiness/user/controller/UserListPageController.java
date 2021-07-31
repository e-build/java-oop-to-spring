package com.bussiness.user.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

import java.io.IOException;

public class UserListPageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.forward("/user/list");
    }
}
