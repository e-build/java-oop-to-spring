package com.spring.http;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    HttpRequest httpRequest;

    private final String testResourceDir = "./src/test/resources";

    @BeforeEach
    void setUp() throws IOException {
        InputStream in  = new FileInputStream(new File(testResourceDir + "/http/request/get_without_param.txt"));
        httpRequest = new HttpRequest(in);
    }

    @Test
    void getMethod() {
        Assertions.assertEquals("GET", httpRequest.getMethod());
    }

    @Test
    void getPath() {
        Assertions.assertEquals("/user/login.html", httpRequest.getPath());
    }

    @Test
    void getVersion() {
        Assertions.assertEquals("HTTP/1.1", httpRequest.getVersion());
    }

    @Test
    void getHeader() {
        Assertions.assertEquals("localhost:8080", httpRequest.getHeader(HttpConstants.Header.HOST));
        Assertions.assertTrue(StringUtils.contains(httpRequest.getHeader(HttpConstants.Header.ACCEPT), "text/html"));
        Assertions.assertTrue(StringUtils.isBlank(httpRequest.getHeader(HttpConstants.Header.CONTENT_LENGTH)));
    }

    @Test
    void getCookie() {
        Assertions.assertEquals("t%3D1613952697495%26u%3D1370f85f318a4c78825808190a1f59f2", httpRequest.getCookies("em_cdn_uid"));
    }


}