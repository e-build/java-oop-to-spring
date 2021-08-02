package com.framework.http;

import com.framework.utils.WebAppUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

public class HttpResponse implements HttpResponseSupport{

    Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private final HttpHeader header;
    private int statusCode;
    private byte[] responseBody;

    public HttpResponse(OutputStream out){
        this.dos = new DataOutputStream(out);;
        statusCode = 200;
        header = new HttpHeader(Maps.newHashMap());
    }

    @Override
    public void sendRedirect(String path) throws IOException {
        this.header.addHeader("Location", path);    // index.html로 리다이렉팅
        this.setStatusCode(302);                    // GET 방식으로 재요청 응답 상태코드
        this.response();
    }

    @Override
    public void forward(String path) throws IOException {
        File file = new File(WebAppUtils.WEBAPP_ROOT_PATH + WebAppUtils.PREFIX + path + WebAppUtils.SUFFIX);
        if ( file.exists() ){
            this.responseBody = Files.readAllBytes(file.toPath());
        } else {
            throw new FileNotFoundException();
        }
        this.response();
    }

    @Override
    public void forwardWebResource(String resourcePath) throws IOException {
        File file = new File(WebAppUtils.WEBAPP_ROOT_PATH  + resourcePath);
        if ( file.exists() ){
            this.responseBody = Files.readAllBytes(file.toPath());
        } else {
            this.statusCode = 404;
        }
        this.response();
    }

    @Override
    public void responseBody() throws IOException {
        byte[] body = this.responseBody;
        if ( body != null ){
            this.header.addHeader(HttpConstants.Header.CONTENT_LENGTH, String.valueOf(body.length));
            dos.write(body, 0, body.length);
        }
    }

    public void responseBody(Object obj) throws IOException {
//        addHeader(HttpConstants.Header.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");

        try {
            dos.writeBytes("HTTP/1.1 " + this.statusCode + " OK \r\n");
            for ( String key : this.header.getData().keySet() ){
                dos.writeBytes(key + ": " + this.header.getData().get(key) + "\r\n");
            }
            dos.writeBytes("\r\n");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            byte[] body = bos.toByteArray();
            dos.write(body, 0, body.length);

        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            dos.flush();
        }


    }

    private void response() throws IOException {
        try {
            dos.writeBytes("HTTP/1.1 " + this.statusCode + " OK \r\n");
            for ( String key : this.header.getData().keySet() ){
                dos.writeBytes(key + ": " + this.header.getData().get(key) + "\r\n");
            }

            dos.writeBytes("\r\n"); // 본문 쓰기 전 개행
            this.responseBody();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            dos.flush();
        }
    }

    private void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String key, String value){
        this.header.addHeader(key, value);
    }

}
