package com.spring.http;

import com.google.common.collect.Maps;
import com.spring.utils.KeyValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String method;
    private String url;
    private String version;
    private Map<String, String> header;
    private Map<String, String> cookies;

    private HttpRequest(Map<String, String> header, Map<String, String> cookies){
        this.header = header;
        this.cookies = cookies;
    }

    public static HttpRequest parseFromHttpString(String httpString){
        HttpRequest request = new HttpRequest(Maps.newHashMap(), Maps.newHashMap());

        String[] httpStringArray = httpString.split("\n");
        request.setHttpRequestOutline(httpStringArray[0].split(" "));

        for ( int i = 1 ; i < httpStringArray.length  ; i++ ){
            KeyValue kv = KeyValue.of(httpStringArray[i].split(": "));
            if ( StringUtils.equals(kv.getKey(), HttpConstants.COOKIE) ){
                request.parseCookies(kv);
                continue;
            }
            request.putHeaderKeyValue(kv.getKey(), kv.getValue());
        }

        return request;
    }

    private void setHttpRequestOutline(String[] firstLine){
        this.method = firstLine[0];
        this.url = firstLine[1];
        this.version = firstLine[2];
    }

    private void putHeaderKeyValue(String key, String value){
        this.header.put(key, value);
    }

    private void parseCookies(KeyValue kv){
        String[] cookies = kv.getValue().split("; ");
        for (String cookie : cookies){
            KeyValue cookieKv = KeyValue.of(cookie.split("="));
            putCookieKeyValue(cookieKv.getKey(), cookieKv.getValue());
        }
    }

    private void putCookieKeyValue(String key, String value){
        this.cookies.put(key, value);
    }

    public String getHeader(String key){
        return this.header.get(key);
    }

    public String getCookie(String key){
        return this.cookies.get(key);
    }

}
