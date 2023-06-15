package com.nyp.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

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
@MapperScan(basePackages = "com.nyp.dao.mapper3", sqlSessionFactoryRef = "sqlSessionFactory3")
public class MybatisDsMysql2Config {

    @Bean(name = "ds3")
    @DependsOn("druidXADataSource3")
    public DataSource ds1DataSource(@Qualifier("druidXADataSource3") DruidXADataSource dataSource ) {
        AtomikosDataSourceBean xaDataSource=new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(dataSource);
        xaDataSource.setUniqueResourceName("ds3");
        return xaDataSource;
    }

    /**
     * 注入DruidXADataSource，Druid对JTA的支持，支持XA协议，采用两阶段事务的提交
     * @return
     */
    @Bean(value = "druidXADataSource3")
    @ConfigurationProperties(prefix = "spring.datasource.thr")
    public DruidXADataSource druidXADataSource3(){
        return new DruidXADataSource();
    }


    @Bean(name = "sqlSessionFactory3")
    public SqlSessionFactoryBean sqlSessionFactory3(@Qualifier("ds3") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置MyBatis的配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("mysql3TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ds3") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
