package com.business.home;

import com.framework.core.mvc.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class HomePageController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.forward("/index");
    }
}
