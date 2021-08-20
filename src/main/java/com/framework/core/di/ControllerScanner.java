package com.framework.core.di;

import com.framework.core.new_mvc.annotation.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {

    public static Set<Class<?>> scan(String scanPackage){
        Reflections reflections = new Reflections(scanPackage);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }
}
