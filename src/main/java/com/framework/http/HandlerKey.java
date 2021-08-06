package com.framework.http;

import com.framework.http.constants.HttpMethod;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Builder
public class HandlerKey {

    private final HttpMethod method;
    private final String path;

    public static HandlerKey of(String method, String path){
        return HandlerKey.builder()
                    .method(HttpMethod.from(method))
                    .path(path)
                    .build();
    }

    public static HandlerKey of(HttpMethod method, String path){
        return HandlerKey.builder()
                .method(method)
                .path(path)
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof HandlerKey))
            return false;

        HandlerKey target = (HandlerKey)obj;
        if( this.method != target.getMethod() )
            return false;
        if( !StringUtils.equals(this.path, target.getPath()) )
            return false;

        return true;
    }
}
