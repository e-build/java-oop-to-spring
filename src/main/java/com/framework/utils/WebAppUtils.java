package com.framework.utils;

import java.io.File;

public class WebAppUtils {
    private WebAppUtils() {}
    // TODO: 프로젝트 루트 디렉토리를 파악하는 자바 네이티브 로직으로 변경 필요
    public static String WEBAPP_ROOT_PATH = "/Users/donggeollee/IdeaProjects/ebuild-github/java-oop-to-spring/webapp";
    public static String PREFIX = "/views";
    public static String SUFFIX = ".html";

    public static boolean existsResourceFile(String resourceFilePath){
        return new File(WEBAPP_ROOT_PATH + resourceFilePath).exists();
    }

}
