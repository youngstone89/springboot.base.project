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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *  
 * {클래스 설명}.
 *
 * @author hyunmyung.yun
 * @version 1.0 2019. 7. 22.
 */
@Configuration
public class PrimaryDatabaseConfig {

    /**
     * Data source Primary.
     *
     * @return the data source
     */
    @Primary
    @Bean(destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSourcePrimary() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Transaction manager Primary.
     *
     * @return the platform transaction manager
     */
    @Primary
    @Bean
    public PlatformTransactionManager transactionManagerPrimary() {
        return new DataSourceTransactionManager(dataSourcePrimary());
    }

    /**
     * Sql session Primary.
     *
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Primary
    @Bean
    public SqlSessionFactory sqlSessionPrimary() throws Exception {

        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(dataSourcePrimary());

        sqlSession.setConfigLocation(new ClassPathResource("/config/mybatis/mybatis-config-primary.xml"));
        sqlSession.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ResourcePatternResolver.CLASSPATH_URL_PREFIX + "/mapper/primary/**/*_SQL.xml"));

        return sqlSession.getObject();
    }

    /**
     * Sql session template Primary.
     *
     * @return the sql session template
     * @throws Exception the exception
     */
    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplatePrimary() throws Exception {
        return new SqlSessionTemplate(sqlSessionPrimary(), ExecutorType.REUSE);
    }

}
