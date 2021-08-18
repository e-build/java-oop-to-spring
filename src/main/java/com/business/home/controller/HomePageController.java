package com.business.home.controller;

import com.framework.core.new_mvc.Controller;
import com.framework.core.new_mvc.RequestMapping;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.constants.HttpMethod;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method = HttpMethod.GET)
    public String goHome(HttpRequest request, HttpResponse response){
        response.forward("/index");
        return null;
    }

}
