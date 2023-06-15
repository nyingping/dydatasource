package com.nyp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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
@MapperScan(basePackages = "com.nyp.dao.mapper1", sqlSessionFactoryRef = "sqlSessionFactory1")
public class MybatisDsMysql1Config {

    @Bean(name = "ds1")
    @ConfigurationProperties(prefix = "spring.datasource.pri")
    @Primary
    public DataSource ds1DataSource() {
        return new DruidDataSource();
    }


    @Bean(name = "sqlSessionFactory1")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory1(@Qualifier("ds1") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置MyBatis的配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer1(){
//        MapperScannerConfigurer msc = new MapperScannerConfigurer();
//        // 设置使用的SqlSessionFactory的名字
//        msc.setSqlSessionFactoryBeanName("sqlSessionFactory1");
//        // 设置映射接口的路径
//        msc.setBasePackage("com.nyp.dao.mapper1");
//        return msc;
//    }

    @Bean("mysqlTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ds1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
