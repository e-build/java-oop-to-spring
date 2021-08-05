package com.framework.http;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class HttpResponse {

    private final DataOutputStream dos;

    public HttpResponse(OutputStream out){
        dos = new DataOutputStream(out);
    }

}
