package com.nyp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @projectName: dydatasource
 * @package: com.nyp.config
 * @className: ChainedTransactionManagerConfig
 * @author: nyp
 * @description: 事务链
 * @date: 2023/6/13 17:13
 * @version: 1.0
 */
//@Configuration
public class ChainedTransactionManagerConfig {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private DataSourceTransactionManager mysqlTransactionManager;
    @Autowired
    private DataSourceTransactionManager mysqlTransactionManager3;

    @Autowired
    @Bean(name = "multiTransactionManager")
    @DependsOn("sessionFactory")
    public PlatformTransactionManager multiTransactionManager() {
        return new ChainedTransactionManager(
                transactionManager, mysqlTransactionManager,mysqlTransactionManager3);
    }
}
