

package com.base.configuration.db;

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

import javax.sql.DataSource;

@Configuration
public class H2DatabaseConfig {


    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.h2")
    public DataSource dataSourceH2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="H2TransactionManager")
    public PlatformTransactionManager transactionManagerH2() {
        return new DataSourceTransactionManager(dataSourceH2());
    }


    @Bean(name="H2SqlSession")
    public SqlSessionFactory sqlSessionH2() throws Exception {

        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(dataSourceH2());

        sqlSession.setConfigLocation(new ClassPathResource("/config/mybatis/mybatis-config-h2.xml"));
        sqlSession.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ResourcePatternResolver.CLASSPATH_URL_PREFIX + "/mapper/h2/**/*_SQL.xml"));

        return sqlSession.getObject();
    }

    @Bean(name="H2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateH2() throws Exception {
        return new SqlSessionTemplate(sqlSessionH2(), ExecutorType.REUSE);
    }

}
