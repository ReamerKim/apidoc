package com.moss.apidoc.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.moss", useDefaultFilters=false, includeFilters=@ComponentScan.Filter(value={Service.class, Repository.class}))
public class RootConfig {
    
    /**
     * MyBatis를 설정 시 함수 호출로 ref를 설정해도 매번 DataSource가 생성되어서 Transaction이 정상적으로 동작하지 않아서
     * 강제로 동일한 dataSource로 설정되도록 하기 위해서 사용하는 property 
     */
    private DataSource apiDataSource = null;
    
    @Bean
    public DataSource apiDataSource() {
        if (null != apiDataSource)
            return apiDataSource;
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ou");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        apiDataSource = dataSource;
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager apiTxMgr() {
        DataSourceTransactionManager apiTxMgr =
                new DataSourceTransactionManager();
        DataSource dataSource = apiDataSource();
        System.out.println(dataSource);
        apiTxMgr.setDataSource(dataSource);
        return apiTxMgr;
    }
    
    @Bean
    public SqlSessionFactory apiSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory =
                new SqlSessionFactoryBean();
        DataSource dataSource = apiDataSource();
        System.out.println(dataSource);
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.moss.apidoc.model");
        return sqlSessionFactory.getObject();
    }
    
    @Bean
    public MapperScannerConfigurer apiMapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer mapperScannerConfigurer =
                new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.moss.apidoc.repository");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("apiSqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
