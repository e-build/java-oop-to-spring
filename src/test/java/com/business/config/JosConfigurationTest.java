package com.business.config;

import com.framework.core.di.annotation.Bean;
import com.framework.core.di.annotation.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@Configuration
public class JosConfigurationTest {

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/jos-h2-db;AUTO_SERVER=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

}