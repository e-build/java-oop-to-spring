package com.bussiness;

import com.bussiness.user.domain.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class Database {

    public static final Map<String, User> User = Maps.newHashMap();

    static {
        User.put("1", new User(1, "1", "1", "1"));
        User.put("2", new User(2, "2", "2", "2"));
        User.put("3", new User(3, "3", "3", "3"));
    }
}
