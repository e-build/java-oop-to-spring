package com.framework.web.server;

import java.io.*;
import java.net.Socket;

import com.framework.http.*;
import com.framework.http.constants.HttpHeader;
import com.framework.http.constants.HttpSession;
import com.framework.utils.UUIDUtils;
import com.framework.utils.WebAppUtils;
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

            // 1. 요청, 응답 인스턴스 생성
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);


            // 2. 응답 헤더 공통 세팅
            response.addHeader(HttpHeader.CONTENT_TYPE.getValue(), parseContentType(request.getHeader(HttpHeader.ACCEPT.getValue()))+";charset=utf-8");
            if (request.getCookie(HttpSession.SESSION_IDENTIFIER.getValue()) == null)
                response.addCookie(HttpSession.SESSION_IDENTIFIER.getValue(), UUIDUtils.newId());

            // 3. HTTP 요청에 해당하는 Controller 확인
            Controller controller = RequestMapping.getController(HandlerKey.of(request.getMethod(), request.getPath()));

            // 4. HTTP 요청 처리
            if (controller != null){
                log.info("[REQUEST] {} {}", request.getMethod(), request.getUrl());
                // 4-1. Controller 로 작업 위임
                controller.service(request, response);
            } else {
                // 4-2. 정적자원 포워딩
                if ( WebAppUtils.existsResourceFile(request.getPath()) )
                    response.forwardResource(request.getPath());
                // 4-3. HTTP 요청에 해당하는 자원 X
                else
                    response.responseBody("404");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String parseContentType(String accept){
        if (accept == null)
            return "*/*";
        return accept.split(",")[0];
    }
}