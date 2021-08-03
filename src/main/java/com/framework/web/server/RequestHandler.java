package com.framework.web.server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import com.framework.utils.JsonUtils;
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

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            // Request Line 추출
            String requestLineString = bufferedReader.readLine();
            String[] requestLine = requestLineString.split(" ");
            String method = requestLine[0];
            String url = requestLine[1];
            String version = requestLine[2];

            // Header 추출
            Map<String, String> headers = Maps.newHashMap();
            String line;
            while( StringUtils.isNotEmpty(line = bufferedReader.readLine())){
                int headerKeyValueSeparatorIdx = line.indexOf(": ");
                headers.put(line.substring(0, headerKeyValueSeparatorIdx), line.substring(headerKeyValueSeparatorIdx + 2));
            }
            log.info("headers : {}", JsonUtils.serialize(headers));

            byte[] body = "Hello World".getBytes();
            if ( StringUtils.equals(method, "GET") ) {
                if ( StringUtils.equals(url, "/") ){
                    File htmlFile = new File(WebAppUtils.WEBAPP_ROOT_PATH + WebAppUtils.PREFIX + "/index.html");
                    body = Files.readAllBytes(htmlFile.toPath());
                } else {

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