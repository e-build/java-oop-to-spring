package com.framework.http;


import java.io.IOException;

public interface Controller {

    void service(HttpRequest request, HttpResponse response) throws IOException;
}
