package com.nyp.controller;

import com.nyp.config.MultiTransaction;
import com.nyp.dao.mapper1.PersonDao;
import com.nyp.dao.mapper2.MovieDao;
import com.nyp.model.Movie;
import com.nyp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: dydatasource
 * @package: com.nyp.controller
 * @className: DyController
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 11:08
 * @version: 1.0
 */
@RestController
@RequestMapping("/dy")
public class DyController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private MovieDao movieDao;

//    @Autowired
//    private Person3Dao person3Dao;

    @RequestMapping("/test")
//    @Transactional(value = "multiTransactionManager", rollbackFor = Exception.class)
    @MultiTransaction
//    @Transactional(value = "xatx", rollbackFor = Exception.class)
    public void test(){
//        Person person3 = new Person();
//        person3.setName("张三3");
//        person3Dao.insert(person3);

        Person person = new Person();
        person.setName("张三");
        personDao.insert(person);

        Movie movie = new Movie();
        movie.setTitle("霸王别姬23");
        movie.setVersion(23);
        movieDao.add(movie);

        int a = 1/0;
    }

    @Transactional(value = "transactionManager")
    @RequestMapping("/neo")
    public void testNeo4j(){
        Movie movie = new Movie();
        movie.setTitle("霸王别姬2");
        movie.setVersion(2);

        movieDao.add(movie);

//        int a = 1/0;
    }

}
