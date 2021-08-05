package com.bussiness;

import com.bussiness.user.User;
import com.google.common.collect.Maps;

import java.util.Map;

public class Database {

    public static final Map<Integer, User> USER = Maps.newHashMap();

    static {
        USER.put(1, new User(1, "1", "1", "1"));
        USER.put(2, new User(2, "2", "2", "2"));
        USER.put(3, new User(3, "3", "3", "3"));
    }
}
