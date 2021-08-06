package com.bussiness.home;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class HomePageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.forward("/index");
    }
}
