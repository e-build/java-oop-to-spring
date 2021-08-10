package com.framework.http;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpCookies {

    private final Map<String,String> cookies;

    public HttpCookies(){
        this.cookies = Maps.newHashMap();
    }

    public HttpCookies(String cookieString){
        this.cookies = Maps.newHashMap();
        String[] cookieArray = cookieString.split("; ");
        for (String keyValue : cookieArray){
            String[] keyValueArr = keyValue.split("=");
            cookies.put(keyValueArr[0], keyValueArr[1]);
        }
    }

    public String getCookie(String key){
        return this.cookies.get(key);
    }

    public void addCookie(String key, String value){
        this.cookies.put(key, value);
    }

    public String toString(){
        List<String> cookieKeyValueList = new ArrayList<>();
        for ( String key : this.cookies.keySet() )
            cookieKeyValueList.add(key+"="+this.cookies.get(key));
        return String.join("; ", cookieKeyValueList);
    }


}
