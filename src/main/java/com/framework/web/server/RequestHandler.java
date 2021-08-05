package com.framework.web.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import com.bussiness.Database;
import com.bussiness.user.User;
import com.framework.http.HttpRequest;
import com.framework.utils.QueryStringUtils;
import com.framework.utils.WebAppUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            String method = request.getMethod();
            String url = request.getUrl();
            String path = request.getPath();

            log.info("[REQUEST] {} {}", method, url);

            // 응답 처리
            Map<String, String> headersToAdd = Maps.newHashMap();
            headersToAdd.put("Content-Type", parseContentType(request.getHeader("Accept"))+";charset=utf-8");
            String statusCode = "200";

            byte[] body = "404".getBytes(StandardCharsets.UTF_8);
            File resourceFile = null;
            final String htmlBaseDirectory = WebAppUtils.WEBAPP_ROOT_PATH + WebAppUtils.PREFIX;
            if ( StringUtils.equals(method, "GET") ) {
                if ( StringUtils.equals(path, "/") ){
                    resourceFile = new File(htmlBaseDirectory + "/index.html");
                } else if ( StringUtils.equals(path, "/user/login") ) {
                    resourceFile = new File(htmlBaseDirectory + "/user/login.html");
                } else if ( StringUtils.equals(path, "/user/isUser") ){ // GET 방식 파라미터 처리
                    User user = findUserByUsername(request.getParameter("username"));
                    if (user != null)
                        resourceFile = new File(htmlBaseDirectory + "/user/userTrue.html");
                    else
                        resourceFile = new File(htmlBaseDirectory + "/user/userFalse.html");
                } else {
                    resourceFile = new File(WebAppUtils.WEBAPP_ROOT_PATH + path);
                }
            }

            // POST 요청 처리 - 본문 파싱(쿼리스트링, JSON)
            if ( StringUtils.equals(method, "POST") ){
                if ( StringUtils.equals(path, "/user/login") ) {
                    Map<String, String> bodyParams = QueryStringUtils.toMap(request.getRequestBody());
                    if ( login(bodyParams.get("username"), bodyParams.get("password")) ){
                        resourceFile = new File(htmlBaseDirectory + "/index.html");
                        headersToAdd.put("Set-Cookie", "login=true");
                        headersToAdd.put("Location", "/");
                        statusCode = "302";
                    } else {
                        resourceFile = new File(htmlBaseDirectory + "/user/login.html");
                    }
                }
            }

            // 응답가능한 자원 체크
            if ( resourceFile != null && resourceFile.exists() )
                body = Files.readAllBytes(resourceFile.toPath());

            DataOutputStream dos = new DataOutputStream(out);
            responseHeader(dos, statusCode, body.length, headersToAdd);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private String parseContentType(String accept){
        return accept.split(",")[0];
    }

    private boolean isLogin(String cookieLoginValue){
        if (cookieLoginValue == null)
            return false;
        return StringUtils.equals(cookieLoginValue, "true");
    }

    private boolean login(String username, String password){
        for ( Integer key : Database.USER.keySet() ){
            if (StringUtils.equals(Database.USER.get(key).getUsername(), username))
                return StringUtils.equals(Database.USER.get(key).getUsername(), password);
        }
        return false;
    }

    private User findUserByUsername(String username){
        for (int key : Database.USER.keySet()){
            if ( StringUtils.equals(username, Database.USER.get(key).getUsername()) )
                return  Database.USER.get(key);
        }
        return null;
    }

    private void responseHeader(DataOutputStream dos, String statusCode, int lengthOfBodyContent, Map<String, String> headersToAdd) {
        try {
            dos.writeBytes("HTTP/1.1 " + statusCode + " OK \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            for ( String key : headersToAdd.keySet() )
                dos.writeBytes( key + ": " + headersToAdd.get(key) + "\r\n");
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