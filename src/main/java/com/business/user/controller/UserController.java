package com.business.user.controller;

import com.business.user.dao.UserDao;
import com.business.user.domain.User;
import com.business.user.service.UserService;
import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.RequestMapping;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.constants.HttpMethod;
import com.framework.utils.QueryStringUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Controller
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

//    public UserController(UserService userService){
//        this.userService = userService;
//    }

    @RequestMapping(method = HttpMethod.GET, value= "/user/login")
    public Object loginPage(HttpRequest request, HttpResponse response){
        response.forward("/user/login");
        return null;
    }

    @RequestMapping(method = HttpMethod.GET, value= "/user/logout")
    public Object logout(HttpRequest request, HttpResponse response){
        request.getSession().invalidate();
        response.sendRedirect("/");
        return null;
    }

    @RequestMapping(method = HttpMethod.GET, value= "/user/register")
    public Object registerPage(HttpRequest request, HttpResponse response){
        response.forward("/user/register");
        return null;
    }

    @RequestMapping(method = HttpMethod.POST, value= "/api/user/register")
    public Object register(HttpRequest request, HttpResponse response){
        Map<String, String> requestBodyMap = QueryStringUtils.toMap(request.getRequestBody());
        userService.register(requestBodyMap);
        response.sendRedirect("/");
        return null;
    }

    @RequestMapping(method = HttpMethod.POST, value= "/api/user/login")
    public Object login(HttpRequest request, HttpResponse response){
        Map<String, String> bodyParams = QueryStringUtils.toMap(request.getRequestBody());
        if ( login(bodyParams.get("username"), bodyParams.get("password")) ){
            response.addCookie("login", "true");

            request.getSession().setAttribute("loginUser", userService.getUserByUsername(bodyParams.get("username")));
            log.info("LOGIN SUCCESS");
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/user/login?logined=false");
        }
        return null;
    }

    @RequestMapping(method = HttpMethod.GET, value= "/api/user/login/check")
    public Object loginCheck(HttpRequest request, HttpResponse response){
        Map<String, String> returnMap = Maps.newHashMap();
        Object user = request.getSession().getAttribute("loginUser");
//        log.info("session 목록 : {}", HttpSessions.getSessions());
        if( user == null )
            returnMap.put("result", "false");
        else
            returnMap.put("result", "true");
        response.responseBody(returnMap);
        return null;
    }

    private boolean login(String username, String password){
        User user = userService.getUserByUsername(username);;
        if (user == null)
            return false;
        return StringUtils.equals(user.getPassword(), password);
    }
}
