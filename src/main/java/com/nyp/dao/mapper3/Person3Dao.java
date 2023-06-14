package com.nyp.dao.mapper3;

import com.nyp.model.Person;
import org.springframework.stereotype.Repository;

/**
 * @projectName: dydatasource
 * @package: com.nyp.dao.mapper3
 * @className: PersonDao
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 14:48
 * @version: 1.0
 */
@Repository
public interface Person3Dao {
    int insert(Person person);
}
