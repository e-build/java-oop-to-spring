package com.framework.core.di;

import com.framework.core.new_mvc.Controller;
import org.reflections.Reflections;

import java.util.Set;

public class ControllerScanner {

    public static Set<Class<?>> scan(String rootPackage){
        Reflections reflections = new Reflections("com.business");
        return reflections.getTypesAnnotatedWith(Controller.class);
    }
}
