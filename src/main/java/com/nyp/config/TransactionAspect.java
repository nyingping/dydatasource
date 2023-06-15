package com.nyp.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Stack;


/**
 * @projectName: dydatasource
 * @package: com.nyp.config
 * @className: TransactionAspect
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 11:35
 * @version: 1.0
 */
@Aspect
@Component
public class TransactionAspect {

    @Resource
    private DataSourceTransactionManager transactionManager;
    @Resource
    private DataSourceTransactionManager mysqlTransactionManager;

    /**
     * 牺牲了@Transactional的事务传播与隔离的丰富性 变成了默认级别
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(MultiTransaction)")
    public Object multiTransaction(ProceedingJoinPoint proceedingJoinPoint) {
        Stack<DataSourceTransactionManager> dtmStack = new Stack<>();
        Stack<TransactionStatus> tsStack = new Stack<>();
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setReadOnly(true);


        TransactionStatus neo4jTransactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        dtmStack.push(transactionManager);
        tsStack.push(neo4jTransactionStatus);
        TransactionStatus transactionStatus = mysqlTransactionManager.getTransaction(new DefaultTransactionDefinition());
        dtmStack.push(mysqlTransactionManager);
        tsStack.push(transactionStatus);

        try {
            Object obj = proceedingJoinPoint.proceed();
            while (!dtmStack.isEmpty()) {
                dtmStack.pop().commit(tsStack.pop());
            }
            return obj;
        } catch (Throwable throwable) {
            while (!dtmStack.isEmpty()) {
                dtmStack.pop().rollback(tsStack.pop());
            }
            throw new RuntimeException(throwable);
        }
    }
}
