package com.spring.http;

import com.spring.utils.IOUtils;
import com.spring.utils.KeyValue;
import lombok.Builder;
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

public final class HttpRequest implements HttpRequestProps{

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final String requestBody;
    private final HttpHeader header;
    private final RequestLine requestLine;
    private final Cookies cookies;
    private final Map<String, String> queryParams;

    @Builder
    @Getter
    private static class RequestLine{
        private String method;
        private String path;
        private String version;

        public static RequestLine of(String requestLine){
            String[] requestLineArr = requestLine.split(" ");
            return RequestLine.builder()
                    .method(requestLineArr[0])
                    .path(requestLineArr[1])
                    .version(requestLineArr[2])
                    .build();
        }
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        // Request Line
        this.requestLine = RequestLine.of(reader.readLine());
        this.queryParams = parseQueryParams(this.requestLine.getPath());
        String line;
        StringBuilder httpHeaderMessageBuilder = new StringBuilder();

        // Header
        while ( StringUtils.isNotBlank(line = reader.readLine()) )
            httpHeaderMessageBuilder.append(line).append("\n");
        this.header = HttpHeader.of(httpHeaderMessageBuilder.toString().split("\n"));

        // Cookie
        this.cookies = Cookies.of(getHeader(HttpConstants.COOKIE).split("; "));

        // Body String
        String contentLength = getHeader("Content-Length");
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

    @Override
    public String getRequestBody() {
        return this.requestBody;
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
