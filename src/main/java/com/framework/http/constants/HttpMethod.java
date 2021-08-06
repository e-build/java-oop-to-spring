package com.framework.http.constants;

import org.apache.commons.lang3.StringUtils;

public enum HttpMethod {
    GET, POST;

    public static HttpMethod from(String methodString){
        if (StringUtils.equals(methodString, HttpMethod.GET.toString()))
            return HttpMethod.GET;
        if (StringUtils.equals(methodString, HttpMethod.POST.toString()))
            return HttpMethod.POST;
        return null;
    }
}
