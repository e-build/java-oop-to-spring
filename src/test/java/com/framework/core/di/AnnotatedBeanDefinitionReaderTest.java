package com.framework.core.di;

import com.business.config.JosConfigurationTest;
import com.framework.core.di.beans.factory.support.DefaultBeanFactory;
import com.framework.core.di.context.annotation.AnnotatedBeanDefinitionReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class AnnotatedBeanDefinitionReaderTest {

    @Test
    public void register_simple(){
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.loadBeanDefinitions(JosConfigurationTest.class);
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
        log.info(" {}", beanFactory.getBean(DataSource.class));

    }

}