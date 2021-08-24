package com.business.user.service;

import com.business.user.dao.UserDao;
import com.business.user.domain.User;
import com.framework.core.di.annotation.Inject;
import com.framework.core.new_mvc.annotation.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public int register(Map<String, String> params){
        String username = params.get("username").trim();
        String password = params.get("password").trim();
        String nickname = params.get("nickname").trim();

        return userDao.insertUser(new User(username, password, nickname));
    }

    public User getUserByUsername(String username){
        return userDao.selectUserByUsername(username);
    }

}
