package com.framework.core.mvc;

import com.framework.http.HttpRequest;
import com.framework.http.HttpResponse;

public interface Controller {

    void service(HttpRequest request, HttpResponse response);
}
