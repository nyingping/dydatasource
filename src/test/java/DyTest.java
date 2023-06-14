import com.nyp.Application;
import com.nyp.dao.mapper1.PersonDao;
import com.nyp.dao.mapper2.MovieDao;
import com.nyp.dao.mapper3.Person3Dao;
import com.nyp.model.Movie;
import com.nyp.model.Person;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @projectName: dydatasource
 * @package: PACKAGE_NAME
 * @className: DyTest
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 9:07
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DyTest {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private Person3Dao person3Dao;

    @Autowired
    private MovieDao movieDao;

    /**
     * 为防止测试数据污染数据库，测试框架默认rollback事务，这里不建议使用@Test进行测试
     * 测试jta
     * 保证mysql XA_RECOVER_ADMIN权限
     * GRANT XA_RECOVER_ADMIN ON *.* TO root@'%' ;
     * flush privileges;
     *
     * 测试结果：异常情况下，两条记录都不插入
     *         正常情况下，两条记录均插入
     * 以上均需要@Rollback配合使用
     */
    @Test
    @Rollback(value = false)
    @Transactional(value = "xatx", rollbackFor = Exception.class)
    public void test(){

        Person person3 = new Person();
        person3.setName("张三3");
        person3Dao.insert(person3);

        Person person = new Person();
        person.setName("张三");
        personDao.insert(person);

        int a = 1/0;
    }

    /**
     * 只测试图库
     * 测试结果： 异常情况不插入，正常情况插入
     */
    @Test
    @Transactional(value = "transactionManager")
    public void testNeo4j(){
        Movie movie = new Movie();
        movie.setTitle("霸王别姬12");
        movie.setVersion(12);

        movieDao.add(movie);

        int a = 1/0;
    }

}
