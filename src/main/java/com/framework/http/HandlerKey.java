package com.framework.http;

import jdk.internal.org.objectweb.asm.Handle;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Handler;

@Builder
@Getter
public class HandlerKey {

    private final String method;
    private final String path;

    public static HandlerKey of(String method, String path){
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
        if ( !(obj instanceof HandlerKey) )
            return false;
        HandlerKey target = (HandlerKey)obj;
        if (!StringUtils.equals(target.getMethod(), this.method))
            return false;
        if (!StringUtils.equals(target.getPath(), this.path))
            return false;
        return true;
    }
}
