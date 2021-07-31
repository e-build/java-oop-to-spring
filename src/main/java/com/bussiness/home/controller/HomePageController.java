package com.bussiness.home.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

import java.io.IOException;

public class HomePageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.forward("/index");
    }
}
