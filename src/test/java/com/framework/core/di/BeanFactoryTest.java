package com.framework.core.di;

import com.framework.core.di.example.RecipeController;
import com.framework.core.di.example.RecipeService;
import com.framework.core.new_mvc.annotation.Controller;
import com.framework.core.new_mvc.annotation.Repository;
import com.framework.core.new_mvc.annotation.Service;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BeanFactoryTest {

    private Reflections reflections;
    private BeanFactory beanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan("com.framework.core.di.example");
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        RecipeController recipeController = beanFactory.getBean(RecipeController.class);
        assertNotNull(recipeController);
        assertNotNull(recipeController.getRecipeService());

        RecipeService recipeService = recipeController.getRecipeService();
        assertNotNull(recipeService.getRecipeRepository());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}