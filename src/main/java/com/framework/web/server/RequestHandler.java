package com.framework.web.server;

import java.io.*;
import java.net.Socket;

import com.bussiness.Database;
import com.bussiness.user.domain.User;
import com.framework.http.*;
import com.framework.utils.WebAppUtils;
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
            HttpResponse response = new HttpResponse(out);
            response.addHeader("Content-Type", parseContentType(request.getHeader("Accept"))+";charset=utf-8");
            log.info("[REQUEST] {} {}", request.getMethod(), request.getUrl());

            Controller controller = RequestMapping.getController(HandlerKey.of(request.getMethod(), request.getPath()));

            if (controller != null){
                controller.service(request, response);
            } else {
                if ( existsResourceFile(request.getPath()) )
                    response.forwardResource(request.getPath());
                else
                    response.responseBody("404");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean existsResourceFile(String resourceFilePath){
        return new File(WebAppUtils.WEBAPP_ROOT_PATH + resourceFilePath).exists();
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

}