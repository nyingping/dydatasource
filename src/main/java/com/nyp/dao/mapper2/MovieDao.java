package com.nyp.dao.mapper2;

import com.nyp.model.Movie;
import org.springframework.stereotype.Repository;

/**
 * @projectName: dydatasource
 * @package: com.nyp.com.nyp.dao.mapper2
 * @className: MoiveDao
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/12 18:55
 * @version: 1.0
 */
@Repository
public interface MovieDao{
    void add(Movie movie);
}
