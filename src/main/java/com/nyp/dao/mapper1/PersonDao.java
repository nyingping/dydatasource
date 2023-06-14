package com.nyp.dao.mapper1;

import com.nyp.model.Person;
import org.springframework.stereotype.Repository;

/**
 * @projectName: dydatasource
 * @package: com.nyp.com.nyp.dao.mapper1
 * @className: PersonDao
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/12 18:55
 * @version: 1.0
 */
@Repository
public interface PersonDao {
    int insert(Person person);
}
