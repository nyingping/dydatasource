import com.nyp.Application;
import com.nyp.dao.mapper1.PersonDao;
import com.nyp.dao.mapper2.MovieDao;
import com.nyp.model.Movie;
import com.nyp.model.Person;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DyTest {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private MovieDao movieDao;

//    @Test
    @Transactional(rollbackFor = Exception.class)
    public void test(){

        Movie movie = new Movie();
        movie.setTitle("霸王别姬");
        movie.setVersion(2);

        movieDao.add(movie);

        Person person = new Person();
        person.setName("张三");

        personDao.insert(person);

        int a = 1/0;
    }

}
