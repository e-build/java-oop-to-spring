package com.framework.http;

import com.framework.utils.IOUtils;
import com.framework.utils.QueryStringUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpRequest {

    private final BufferedReader br;
    private final RequestLine requestLine;
    private final Map<String,String> headers;
    private final Map<String,String> cookies;
    private final Map<String,String> parameters;
    private final String requestBody;

    public HttpRequest(InputStream in) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(in));
        // Request Line 추출
        String requestLineString = this.br.readLine();
        String[] requestLine = requestLineString.split(" ");
        String method = requestLine[0];
        String url = requestLine[1];
        String path = requestLine[1];
        String version = requestLine[2];
        Map<String, String> params = Maps.newHashMap();
        if (existsParam(url)){
            path = parsePath(url);
            params = parseQueryString(url);
        }
        this.requestLine = new RequestLine(method, url, path, version);
        this.parameters = params;

        // Header 추출
        this.headers = Maps.newHashMap();
        String line;
        while( StringUtils.isNotEmpty(line = this.br.readLine())){
            int headerKeyValueSeparatorIdx = line.indexOf(": ");
            headers.put(line.substring(0, headerKeyValueSeparatorIdx), line.substring(headerKeyValueSeparatorIdx + 2));
        }

        // Cookie 추출
        this.cookies = parseCookie(this.headers.get("Cookie"));

        // Body 추출
        String contentLength = this.headers.get("Content-Length");
        String requestBody = null;
        if ( contentLength != null)
            requestBody = IOUtils.readData(this.br, Integer.parseInt(contentLength));
        this.requestBody = requestBody;
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

    private Map<String, String> parseCookie(String cookieString){
        Map<String, String> cookies = Maps.newHashMap();
        String[] cookieArray = cookieString.split("; ");
        for (String keyValue : cookieArray){
            String[] keyValueArr = keyValue.split("=");
            cookies.put(keyValueArr[0], keyValueArr[1]);
        }
        return cookies;
    }

    public String getMethod(){
        return this.requestLine.getMethod();
    }

    public String getUrl(){
        return this.requestLine.getUrl();
    }

    public String getPath(){
        return this.requestLine.getPath();
    }

    public String getVersion(){
        return this.requestLine.getVersion();
    }

    public String getHeader(String key){
        return this.headers.get(key);
    }

    public String getParameter(String key){
        return this.parameters.get(key);
    }

    public String getCookie(String key){
        return this.cookies.get(key);
    }

    public String getRequestBody(){
        return this.requestBody;
    }


}
