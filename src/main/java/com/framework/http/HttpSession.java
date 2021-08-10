package com.framework.http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSession {

    private final String id;
    private final Map<String, Object> session;

    public HttpSession(String id){
        this.id = id;
        this.session = Maps.newHashMap();
    }

    public String getId(){
        return this.id;
    };

    public void setAttribute(String name, Object value){
        session.put(name, value);
    };

    public Object getAttribute(String name){
        return session.get(name);
    };

    public void removeAttribute(String name){
        session.remove(name);
    };

    public void invalidate(){
        HttpSessions.remove(this.id);
    };

}
