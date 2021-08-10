package com.framework.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    HttpRequest request;

    @BeforeEach
    void setUp() throws IOException {
        File file = new File("/Users/donggeollee/IdeaProjects/ebuild-github/java-oop-to-spring/src/test/resources/http/request/get_without_param.txt");
        InputStream in = new FileInputStream(file);
        request = new HttpRequest(in);
    }

    @Test
    void requestLineTest(){
        Assertions.assertEquals("GET", request.getMethod());
        Assertions.assertEquals("/user/isUser?username=1", request.getUrl());
        Assertions.assertEquals("/user/isUser", request.getPath());
        Assertions.assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    void headerTest(){
        Assertions.assertEquals("localhost:8080", request.getHeader("Host"));
        Assertions.assertEquals("keep-alive", request.getHeader("Connection"));
        Assertions.assertEquals("max-age=0", request.getHeader("Cache-Control"));
        Assertions.assertEquals("?0", request.getHeader("sec-ch-ua-mobile"));
        Assertions.assertEquals("1", request.getHeader("Upgrade-Insecure-Requests"));
        Assertions.assertEquals("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36", request.getHeader("User-Agent"));
        Assertions.assertEquals("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9", request.getHeader("Accept"));
        Assertions.assertEquals("none", request.getHeader("Sec-Fetch-Site"));
        Assertions.assertEquals("navigate", request.getHeader("Sec-Fetch-Mode"));
        Assertions.assertEquals("?1", request.getHeader("Sec-Fetch-User"));
        Assertions.assertEquals("document", request.getHeader("Sec-Fetch-Dest"));
        Assertions.assertEquals("gzip, deflate, br", request.getHeader("Accept-Encoding"));
        Assertions.assertEquals("ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7", request.getHeader("Accept-Language"));
    }

    @Test
    void getParamQueryStringTest(){
        Assertions.assertEquals("1", request.getParameter("username"));
    }

    @Test
    void postBodyTest(){

    }

    @Test
    void cookieTest(){
        Assertions.assertEquals("true", request.getCookie("login"));
    }

    @Test
    void getSessionTest(){
        Assertions.assertEquals("623215a7-9cfa-4d76-85d6-e8199446d9c7", request.getSession().getId());;
    }
}