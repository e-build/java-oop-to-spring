package com.framework.core.di;

import com.framework.core.di.annotation.ComponentScan;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext{

    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses){
        Object[] basePackages = findBasePackages(annotatedClasses);
        this.beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(annotatedClasses);

        if (basePackages.length > 0){
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
            scanner.doScan(findBasePackages(annotatedClasses));
        }
        beanFactory.initialize();
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses){
        List<Object> basePackages = Lists.newArrayList();
        for ( Class<?> annotatedClass : annotatedClasses ){
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null)
                continue;
            for (String basePackage : componentScan.value())
                log.info("Component Scan basePackage : {}", basePackage);
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<?> clazz) {
        return (T)beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
