package com.nyp.config;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MultiTransaction {

    // 这里也可以仿照@Transactional加上这些参数

//    @AliasFor("transactionManager")
//    String value() default "";
//
//    @AliasFor("value")
//    String transactionManager() default "";
//
//    Propagation propagation() default Propagation.REQUIRED;
//
//    Isolation isolation() default Isolation.DEFAULT;
//
//    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;
//
//    boolean readOnly() default false;
}
