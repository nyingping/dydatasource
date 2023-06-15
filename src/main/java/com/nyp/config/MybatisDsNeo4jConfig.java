package com.nyp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.nyp.dao.mapper2", sqlSessionFactoryRef = "sqlSessionFactoryNeo4j")
public class MybatisDsNeo4jConfig {

    @Bean(name = "dsNeo4j")
    @ConfigurationProperties(prefix = "spring.datasource.sec")
    public DataSource dsNeoDataSource() {
        return new DruidDataSource();
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

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer2(){
//        MapperScannerConfigurer msc = new MapperScannerConfigurer();
//        // 设置使用的SqlSessionFactory的名字,这个数据源扫描下面的目录下的mapper
//        msc.setSqlSessionFactoryBeanName("sqlSessionFactoryNeo4j");
//        // 设置映射接口的路径
//        msc.setBasePackage("com.nyp.dao.mapper2");
//        return msc;
//    }

    @Bean("transactionManager")
    @Primary
    public DataSourceTransactionManager neo4jTransactionManager(@Qualifier("dsNeo4j") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
