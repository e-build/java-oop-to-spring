package com.bussiness.user.controller;

import com.bussiness.user.dao.UserDao;
import com.bussiness.user.domain.User;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.http.HttpSessions;
import com.framework.http.constants.HttpSession;
import com.framework.utils.QueryStringUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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