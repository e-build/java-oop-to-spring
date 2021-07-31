package com.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    public static String readData(BufferedReader br, int length) throws IOException {
        char[] body = new char[length];
        br.read(body, 0, length);
        return String.copyValueOf(body);
    }

}
