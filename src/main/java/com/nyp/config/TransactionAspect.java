package com.nyp.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @projectName: dydatasource
 * @package: com.nyp.config
 * @className: TransactionAspect
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 11:35
 * @version: 1.0
 */
//@Aspect
//@Component
public class TransactionAspect {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private DataSourceTransactionManager mysqlTransactionManager;
    @Autowired
    private DataSourceTransactionManager mysqlTransactionManager3;

    /**
     * 牺牲了@Transactional的事务传播与隔离的丰富性 变成了默认级别
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(MultiTransaction)")
    public Object multiTransaction(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("around=============================");
        TransactionStatus neo4jTransactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        TransactionStatus jpaTransactionStatus = mysqlTransactionManager.getTransaction(new DefaultTransactionDefinition());
        TransactionStatus jpaTransactionStatus3 = mysqlTransactionManager3.getTransaction(new DefaultTransactionDefinition());
        try {
            Object obj = proceedingJoinPoint.proceed();
            mysqlTransactionManager3.commit(jpaTransactionStatus3);
            mysqlTransactionManager.commit(jpaTransactionStatus);
            transactionManager.commit(neo4jTransactionStatus);
            return obj;
        } catch (Throwable throwable) {
            mysqlTransactionManager3.rollback(jpaTransactionStatus3);
            mysqlTransactionManager.rollback(jpaTransactionStatus);
            transactionManager.rollback(neo4jTransactionStatus);
            System.err.println("multiTransaction fail:"+ throwable);
            throw new RuntimeException(throwable);
        }
    }


}
