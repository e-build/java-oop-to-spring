package com.framework.web.server;

import java.io.*;
import java.net.Socket;

import com.framework.core.mvc.Controller;
import com.framework.core.new_mvc.HandlerExecution;
import com.framework.core.new_mvc.HandlerMapping;
import com.framework.core.new_mvc.adapter.HandlerAdapter;
import com.framework.http.*;
import com.framework.http.constants.HttpHeader;
import com.framework.http.constants.HttpSession;
import com.framework.utils.UUIDUtils;
import com.framework.utils.WebAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatchRequest extends Thread {
    private static final Logger log = LoggerFactory.getLogger(DispatchRequest.class);

    private Socket connection;

    public DispatchRequest(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            // 1. 요청, 응답 인스턴스 생성
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            // 2. 응답 헤더 공통 세팅
            response.addHeader(HttpHeader.CONTENT_TYPE.getValue(), parseContentType(request.getHeader(HttpHeader.ACCEPT.getValue()))+";charset=utf-8");
            if (request.getCookie(HttpSession.SESSION_IDENTIFIER.getValue()) == null)
                response.addCookie(HttpSession.SESSION_IDENTIFIER.getValue(), UUIDUtils.newId());

            // 3. HTTP 요청에 해당하는 Controller 확인
            Object handler = getHandler(request);

            if (handler == null){
                // 4-1. 자원 처리
                if ( WebAppUtils.existsResourceFile(request.getPath()) )
                    response.forwardResource(request.getPath());
                else
                    response.responseBody("404");
            } else {
                // 4-2. HTTP 요청 처리
                log.info("[REQUEST] {} {}", request.getMethod(), request.getUrl());
                for ( HandlerAdapter handlerAdapter : WebServer.getHandlerAdapters() ) {
                    if (handlerAdapter.support(handler))
                        handlerAdapter.handle(request, response, handler);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isLogin(String loginCookie){
        log.info("loginCookie : {}", loginCookie);
        if (StringUtils.isBlank(loginCookie))
            return false;
        return StringUtils.equals(loginCookie, "true");
    }

    private boolean isFirstConnect(String sessionCookie){
        return StringUtils.isBlank(sessionCookie);
    }

    private String parseContentType(String accept){
        if (accept == null)
            return "*/*";
        return accept.split(",")[0];
    }

    private Object getHandler(HttpRequest request) {
        for (HandlerMapping handlerMapping : WebServer.getHandlerMappings() ){
            Object handler = handlerMapping.getHandler(request);
            if( handler != null )
                return handler;
        }
        return null;
    }
}