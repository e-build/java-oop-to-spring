package com.business.user.controller;

import com.business.user.dao.UserDao;
import com.business.user.domain.User;
import com.framework.http.Controller;
import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;
import com.framework.utils.QueryStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Slf4j
public class UserLoginApiController implements Controller {

    private final UserDao userDao = new UserDao();

    @Override
    public void service(HttpRequest request, HttpResponse response) {

        Map<String, String> bodyParams = QueryStringUtils.toMap(request.getRequestBody());
        if ( login(bodyParams.get("username"), bodyParams.get("password")) ){
            response.addCookie("login", "true");
            request.getSession().setAttribute("loginUser", userDao.selectUserByUsername(bodyParams.get("username")));
            log.info("LOGIN SUCCESS");
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/user/login?logined=false");
        }
    }

    private boolean login(String username, String password){
        User user = userDao.selectUserByUsername(username);
        if (user == null)
            return false;
        return StringUtils.equals(user.getPassword(), password);
    }
}
