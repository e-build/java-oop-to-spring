package com.framework.http;

import com.framework.utils.JsonUtils;
import com.framework.utils.WebAppUtils;
import com.google.common.collect.Maps;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse {

    Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private final Map<String, String> headers;
    private final HttpCookies cookies;
    private byte[] body;
    @Setter private int statusCode;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.headers = Maps.newHashMap();
        this.statusCode = 200;
        this.cookies = new HttpCookies();
    }

    private void responseHeader() {
        try {
            this.dos.writeBytes("HTTP/1.1 " + String.valueOf(this.statusCode) + " OK \r\n");
            this.dos.writeBytes("Content-Length: " + this.body.length + "\r\n");
            for ( String key : this.headers.keySet() )
                dos.writeBytes( key + ": " + this.headers.get(key) + "\r\n");
            dos.writeBytes(  "Set-Cookie: " + cookies.toString() + "\r\n");
            this.dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody() {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseflush(){
        responseHeader();
        responseBody();
    }

    public void sendRedirect(String path){
        this.addHeader("Location", path);
        this.setStatusCode(302);
        this.body = "".getBytes(StandardCharsets.UTF_8);
        responseflush();
    }

    public void forward(String path) {
        try{
            File resourceFile = new File(WebAppUtils.WEBAPP_ROOT_PATH + WebAppUtils.PREFIX + path + WebAppUtils.SUFFIX);
            if ( resourceFile.exists() )
                this.body = Files.readAllBytes(resourceFile.toPath());
        } catch(IOException e){
            log.error(e.getMessage());
            // TODO: body에 에러 페이지 전달 처리
            this.body = "404".getBytes(StandardCharsets.UTF_8);
        }
        responseflush();
    }

    public void forwardResource(String path) {
        try {
            File resourceFile = new File(WebAppUtils.WEBAPP_ROOT_PATH + path);
            if (resourceFile.exists())
                this.body = Files.readAllBytes(resourceFile.toPath());
        } catch (IOException e) {
            log.error(e.getMessage());
            // TODO: body에 에러 페이지 전달 처리
            this.body = "404".getBytes(StandardCharsets.UTF_8);
        }
        responseflush();
    }

    public void responseBody(Object obj){
        String jsonString = JsonUtils.serialize(obj);
        body = jsonString.getBytes(StandardCharsets.UTF_8);
        addHeader("Content-Type", "application/json;charset=UTF-8");
        responseflush();
    }

    public void addHeader(String key, String value){
        this.headers.put(key, value);
    }

    public void addCookie(String key, String value){
        this.cookies.addCookie(key, value);
    }

}
