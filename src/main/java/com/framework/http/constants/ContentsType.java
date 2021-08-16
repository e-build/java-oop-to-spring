package com.framework.http.constants;

import lombok.Getter;

public enum ContentsType {

    APPLICATION_ATOM_XML("application/atom+xml", "ISO-8859-1"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded", "ISO-8859-1"),
    APPLICATION_JSON("application/json", "UTF-8"),
    APPLICATION_OCTET_STREAM("application/octet-stream", "ISO-8859-1"),
    APPLICATION_SOAP_XML("application/soap+xml", "ISO-8859-1"),
    APPLICATION_SVG_XML("application/svg+xml", "ISO-8859-1"),
    APPLICATION_XHTML_XML("application/xhtml+xml", "ISO-8859-1"),
    APPLICATION_XML("application/xml", "ISO-8859-1"),
    IMAGE_BMP("image/bmp", "ISO-8859-1"),
    IMAGE_GIF("image/gif", "ISO-8859-1"),
    IMAGE_JPEG("image/jpeg", "ISO-8859-1"),
    IMAGE_PNG("image/png", "ISO-8859-1"),
    IMAGE_SVG("image/svg+xml", "ISO-8859-1"),
    IMAGE_TIFF("image/tiff", "ISO-8859-1"),
    IMAGE_WEBP("image/webp", "ISO-8859-1"),
    MULTIPART_FORM_DATA("multipart/form-data", "ISO-8859-1"),
    TEXT_HTML("text/html", "ISO-8859-1"),
    TEXT_PLAIN("text/plain", "ISO-8859-1"),
    TEXT_XML("text/xml", "ISO-8859-1"),
    WILDCARD("*/*", "ISO-8859-1");

    @Getter
    private final String value;
    @Getter
    private final String charSet;


    ContentsType(String value, String charSet){
        this.value = value;
        this.charSet = charSet;
    }

    public String toString(){
        return this.value + "; " + this.charSet;
    }
}
