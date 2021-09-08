package com.framework.core.di;

import com.business.config.JosConfigurationTest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AnnotatedBeanDefinitionReaderTest {

    @Test
    public void register_simple(){
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(JosConfigurationTest.class);
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
        log.info(" {}", beanFactory.getBean(DataSource.class));

    }

}