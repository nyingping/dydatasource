package com.nyp.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @projectName: dydatasource
 * @package: com.nyp.config
 * @className: MybatisDs1Config
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/12 18:47
 * @version: 1.0
 */
//@Configuration
//@EnableTransactionManagement
public class MybatisDsNeo4jConfig {

    /**
     * 注入DruidXADataSource，Druid对JTA的支持，支持XA协议，采用两阶段事务的提交
     * @return
     */
    @Bean(value = "druidXADataSourceNeo4j")
    @ConfigurationProperties(prefix = "spring.datasource.sec1")
    public DruidXADataSource druidXADataSourceNeo(){
        return new DruidXADataSource();
    }

    @Bean(name = "dsNeo4j")
    public DataSource dsNeoDataSource(
            @Qualifier("druidXADataSourceNeo4j") DruidXADataSource dataSource
    ) {
        AtomikosDataSourceBean xaDataSource=new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(dataSource);
        xaDataSource.setUniqueResourceName("dsNeo4j");
        return xaDataSource;
//                return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactoryNeo4j")
    public SqlSessionFactoryBean sqlSessionFactory2(@Qualifier("dsNeo4j") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置MyBatis的配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer2(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        // 设置使用的SqlSessionFactory的名字,这个数据源扫描下面的目录下的mapper
        msc.setSqlSessionFactoryBeanName("sqlSessionFactoryNeo4j");
        // 设置映射接口的路径
        msc.setBasePackage("com.nyp.dao.mapper2");
        return msc;
    }


    @Bean("transactionManager")
    @Primary
    public DataSourceTransactionManager neo4jTransactionManager(@Qualifier("dsNeo4j") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
