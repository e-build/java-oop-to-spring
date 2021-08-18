package com.business.user.controller;

import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public class UserLogoutController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        request.getSession().invalidate();
        response.sendRedirect("/");
    }
}
