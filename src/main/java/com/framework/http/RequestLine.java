package com.framework.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RequestLine {
    private final String method;
    private final String url;
    private final String path;
    private final String version;
}
