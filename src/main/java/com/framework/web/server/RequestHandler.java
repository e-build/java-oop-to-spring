package com.framework.web.server;

import com.framework.http.*;
import com.framework.utils.WebAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable  {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            log.info("[HTTP REQUEST] : {} {}", httpRequest.getMethod(), httpRequest.getPath());

            Controller controller = RequestMapping.getController(HandlerKey.of(httpRequest.getMethod(), httpRequest.getPath()));
            httpResponse.addHeader(HttpConstants.Header.CONTENT_TYPE, httpRequest.getSimpleAccept() + "; charset=UTF-8");
            if ( controller == null ) {
                httpResponse.forwardWebResource(httpRequest.getPath());
            } else {
                controller.service(httpRequest, httpResponse);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
