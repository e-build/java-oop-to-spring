package com.business.config;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.framework.core.db.JdbcTemplate;
import com.framework.core.di.annotation.Bean;
import com.framework.core.di.annotation.ComponentScan;
import com.framework.core.di.annotation.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.business","com.framework"})
public class JosConfiguration {

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/jos-h2-db;AUTO_SERVER=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
