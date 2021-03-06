package com.framework.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    HttpResponse response;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        File file = new File("/Users/donggeollee/IdeaProjects/ebuild-github/java-oop-to-spring/src/test/resources/http/response/html.txt");
        OutputStream out = new FileOutputStream(file);
        response = new HttpResponse(out);
    }

    @Test
    void sendRedirect(){

    }

    @Test
    void forward(){

    }

    @Test
    void forwardResource(){

    }

    @Test
    void responseBody(){

    }

    @Test
    void responseCssFile(){

    }



}