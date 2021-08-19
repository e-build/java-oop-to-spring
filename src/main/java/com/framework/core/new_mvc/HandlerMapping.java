package com.framework.core.new_mvc;

import com.framework.http.HttpRequest;

public interface HandlerMapping {

    Object getHandler(HttpRequest request);
}
