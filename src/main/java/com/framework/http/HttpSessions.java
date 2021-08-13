package com.framework.http;

import com.framework.utils.UUIDUtils;
import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessions {

    private static final Map<String, HttpSession> sessions = Maps.newHashMap();

    public static HttpSession getSession(String id){
        if (!sessions.containsKey(id))
            sessions.put(id, new HttpSession(id));
        return sessions.get(id);
    }

    public static void remove(String id){
        sessions.remove(id);
    }

    public static boolean exists(String id){
        return sessions.containsKey(id);
    }

    public static Map<String, HttpSession> getSessions(){
        return sessions;
    }
}
