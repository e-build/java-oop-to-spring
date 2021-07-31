package com.bussiness.user.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserLoginPageController implements Controller {
    Logger log = LoggerFactory.getLogger(UserLoginPageController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.forward("/user/login");
    }

}
