package com.framework.http;

import com.framework.utils.IOUtils;
import com.framework.utils.KeyValue;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpRequest implements HttpRequestSupport{

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpHeader header;
    private final RequestLine requestLine;
    private final Cookies cookies;
    private final Map<String, String> queryParams;
    private final String requestBody;

    @Getter
    private static class RequestLine{
        private final String method;
        private final String path;
        private final String version;
        private final String url;

        public RequestLine(String requestLine){
            String[] requestLineArr = requestLine.split(" ");
            this.method = requestLineArr[0];
            this.url = requestLineArr[1];
            this.path = getParsePath(requestLineArr[1]);
            this.version = requestLineArr[2];
        }

        private String getParsePath(String url){
            return url.split("\\?")[0];
        }
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        // Request Line
        this.requestLine = new RequestLine(reader.readLine());
        this.queryParams = parseQueryParams(this.requestLine.getUrl());
        String line;
        StringBuilder httpHeaderMessageBuilder = new StringBuilder();

        // Header
        while ( StringUtils.isNotBlank(line = reader.readLine()) )
            httpHeaderMessageBuilder.append(line).append("\n");
        this.header = new HttpHeader(httpHeaderMessageBuilder.toString().split("\n"));

        // Cookie
        this.cookies = new Cookies(getHeader(HttpConstants.COOKIE).split("; "));

        // Body String
        String contentLength = getHeader(HttpConstants.Header.CONTENT_LENGTH);
        if ( StringUtils.isNotBlank(contentLength) )
            this.requestBody = readRequestBody(reader, Integer.parseInt(contentLength));
        else
            this.requestBody = "";
    }

    public String readRequestBody(BufferedReader br, int contentLength){
        try{
            return IOUtils.readData(br, contentLength);
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String getPath() {
        return this.requestLine.getPath();
    }

    @Override
    public String getMethod() {
        return this.requestLine.getMethod();
    }

    @Override
    public String getVersion() {
        return this.requestLine.getVersion();
    }

    @Override
    public String getHeader(String key) {
        return this.header.getData().get(key);
    }

    @Override
    public String getCookies(String key) {
        return this.cookies.getData().get(key);
    }

    @Override
    public String getParameter(String key) {
        return this.queryParams.get(key);
    }

    public Map<String, String> getParameters() {
        return this.queryParams;
    }

    @Override
    public String getRequestBody() {
        return this.requestBody;
    }

    public String getSimpleAccept(){
        return getHeader(HttpConstants.Header.ACCEPT).split(",")[0];
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    private Map<String, String> parseQueryParams(String path){
        if ( !StringUtils.contains(path, "?"))
            return null;
        return KeyValue.toMap(toKeyValueArray(path));
    }

    private String[] toKeyValueArray(String path){
        return path.split("\\?")[1].split("&");
    }
}
