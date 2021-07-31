package com.spring.web.server;

import com.spring.http.HttpConstants;
import com.spring.http.HttpRequest;
import com.spring.utils.WebAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RequestHandler implements Runnable  {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            log.info("[HTTP REQUEST] : {} {}", httpRequest.getMethod(), httpRequest.getPath());

            byte[] body = "Hello World".getBytes();
            File htmlFile;
            if ( StringUtils.equals(httpRequest.getMethod(), HttpConstants.Method.GET) ) {
                String webResource = WebAppUtils.WEBAPP_ROOT_PATH + WebAppUtils.PREFIX;
                if ( StringUtils.equals(httpRequest.getPath(), "/home") )
                    webResource += "/index";
                else
                    webResource += httpRequest.getPath();
                File webResourceFile = new File(webResource + WebAppUtils.SUFFIX);
                if ( webResourceFile.exists() && !webResourceFile.isDirectory() )
                    body = Files.readAllBytes(webResourceFile.toPath());
                else
                    body = "존재하지 않는 페이지입니다".getBytes();
            }

            if ( StringUtils.equals(httpRequest.getMethod(), HttpConstants.Method.POST) ){
                if ( StringUtils.equals(httpRequest.getPath(), "/user/login") )  {
                    log.info("111");



                }

            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
