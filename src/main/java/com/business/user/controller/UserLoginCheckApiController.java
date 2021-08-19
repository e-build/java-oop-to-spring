package com.business.user.controller;

import com.business.user.dao.UserDao;
import com.framework.core.mvc.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.HttpSessions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class UserLoginCheckApiController implements Controller {

    private final UserDao userDao = new UserDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        Map<String, String> returnMap = Maps.newHashMap();
        Object user = request.getSession().getAttribute("loginUser");
        log.info("session 목록 : {}", HttpSessions.getSessions());
        if( user == null )
            returnMap.put("result", "false");
        else
            returnMap.put("result", "true");
        response.responseBody(returnMap);
    }

}