package com.framework.http;

import java.io.IOException;

interface HttpResponseSupport {

    void sendRedirect(String path) throws IOException;
    void forward(String path) throws IOException;
    void forwardWebResource(String resource) throws IOException;
    void responseBody() throws IOException;

}