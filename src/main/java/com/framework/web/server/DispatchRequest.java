package com.framework.web.server;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import com.bussiness.user.dao.UserDao;
import com.bussiness.user.domain.User;
import com.framework.utils.IOUtils;
import com.framework.utils.QueryStringUtils;
import com.framework.utils.WebAppUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatchRequest extends Thread {
    private static final Logger log = LoggerFactory.getLogger(DispatchRequest.class);

    private final UserDao userDao = new UserDao();

    private Socket connection;

    public DispatchRequest(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // Request Line 추출
            String requestLineString = bufferedReader.readLine();
            String[] requestLine = requestLineString.split(" ");
            String method = requestLine[0];
            String url = requestLine[1];
            String path = requestLine[1];
            Map<String, String> params = Maps.newHashMap();
            if (existsParam(url)){
                path = parsePath(url);
                params = parseQueryString(url);
            }

            String version = requestLine[2];
            log.info("[REQUEST] {} {}", method, url);

            // Header 추출
            Map<String, String> headers = Maps.newHashMap();
            String line;
            while( StringUtils.isNotEmpty(line = bufferedReader.readLine())){
                int headerKeyValueSeparatorIdx = line.indexOf(": ");
                headers.put(line.substring(0, headerKeyValueSeparatorIdx), line.substring(headerKeyValueSeparatorIdx + 2));
            }

            // Cookie 추출
            String cookieString = headers.get("Cookie");
            Map<String, String> cookies = parseCookie(cookieString);

            // Body 추출
            String contentLength = headers.get("Content-Length");
            String requestBody = null;
            if ( contentLength != null)
                requestBody = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
            requestBody = requestBody != null ? URLDecoder.decode(requestBody) : null;

            // 응답 처리
            Map<String, String> headersToAdd = Maps.newHashMap();
            headersToAdd.put("Content-Type", parseContentType(headers.get("Accept"))+";charset=utf-8");
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
                    User user = userDao.selectUserByUsername(params.get("username"));
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
                    Map<String, String> bodyParams = QueryStringUtils.toMap(requestBody);
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
        User user = userDao.selectUserByUsername(username);
        if( user == null)
            return false;
        return StringUtils.equals(user.getPassword(), password);
    }

    private Map<String, String> parseCookie(String cookieString){
        Map<String, String> cookies = Maps.newHashMap();
        String[] cookieArray = cookieString.split("; ");
        for (String keyValue : cookieArray){
            String[] keyValueArr = keyValue.split("=");
            cookies.put(keyValueArr[0], keyValueArr[1]);
        }
        return cookies;
    }

    private boolean existsParam(String url){
        return url.contains("?");
    }

    private String parsePath(String url){
        int idx = url.indexOf("?");
        return url.substring(0, idx);
    }

    private Map<String, String> parseQueryString(String url){
        int idx = url.indexOf("?");
        return QueryStringUtils.toMap(url.substring(idx+1));
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