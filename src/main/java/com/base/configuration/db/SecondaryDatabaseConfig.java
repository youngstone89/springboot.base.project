/**
 * MariaDatabaseConfig.java
 * 
 * @author hyunmyung.yun
 * @version 1.0 2017. 1. 25.
 *
 * Copyright (c) 2016 BESPIN GLOBAL. 
 * 36 Jangmi-ro, Bundang-gu, Seongnam-si Gyeonggi-do Korea , 13496 
 * All rights reserved.  
 */

package com.base.configuration.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SecondaryDatabaseConfig {

    /**
     * Data source asset.
     *
     * @return the data source
     */
    @Bean(destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.secondary")
    public DataSource dataSourceSecondary() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Transaction manager Secondary.
     *
     * @return the platform transaction manager
     */
    @Bean(name="secondaryTransactionManager")
    public PlatformTransactionManager transactionManagerSecondary() {
        return new DataSourceTransactionManager(dataSourceSecondary());
    }

    /**
     * Sql session Secondary.
     *
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean(name="SecondarySqlSession")
    public SqlSessionFactory sqlSessionSecondary() throws Exception {

        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(dataSourceSecondary());

        sqlSession.setConfigLocation(new ClassPathResource("/config/mybatis/mybatis-config-secondary.xml"));
        sqlSession.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ResourcePatternResolver.CLASSPATH_URL_PREFIX + "/mapper/secondary/**/*_SQL.xml"));

        return sqlSession.getObject();
    }

    /**
     * Sql session template Secondary.
     *
     * @return the sql session template
     * @throws Exception the exception
     */
    @Bean(name="secondarySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateSecondary() throws Exception {
        return new SqlSessionTemplate(sqlSessionSecondary(), ExecutorType.REUSE);
    }

}
