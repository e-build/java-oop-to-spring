package com.framework.http;

interface HttpRequestSupport {

    String getPath();
    String getMethod();
    String getVersion();
    String getHeader(String key);
    String getCookies(String key);
    String getParameter(String key);
    String getRequestBody();
}
