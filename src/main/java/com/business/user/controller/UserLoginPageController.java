package com.business.user.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class UserLoginPageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.forward("/user/login");
    }
}
