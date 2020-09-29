package cn.denghanxi;

import cn.denghanxi.data.DBUtil;
import cn.denghanxi.model.Person;
import cn.denghanxi.data.mapper.PersonJavaMapper;
import cn.denghanxi.data.mapper.PersonMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class CreateDatabaseTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Logger logger = LoggerFactory.getLogger(CreateDatabaseTest.class);
    @BeforeAll
    static void beforeAll() throws IOException {
        String resourcePath = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        DBUtil.initDB(sqlSessionFactory);
    }

    @AfterAll
    static void afterAll(){

    }

    @Test
    void testCreate() {
        try (SqlSession session = sqlSessionFactory.openSession()) {

//            Person person = session.selectOne("cn.denghanxi.data.mapper.PersonMapper.selectPerson", 101);
            Person jack = new Person("Jack", 12, "Tianjing");
            PersonJavaMapper personJavaMapper = session.getMapper(PersonJavaMapper.class);

//            personMapper.initDatabase();
            personJavaMapper.save(jack);
            Person person = personJavaMapper.getPersonByName("Jack");
            System.out.println(person);
            Person person1 = session.selectOne("cn.denghanxi.data.mapper.PersonMapper.selectPerson", person.getId());
            System.out.println(person1);
            Assertions.assertEquals("Jack", person1.getName());

            PersonMapper personMapper = session.getMapper(PersonMapper.class);
            Person person2 = personMapper.selectPerson(person.getId());
            Assertions.assertEquals("Jack", person2.getName());
            Assertions.assertEquals(jack.getAddress(), person2.getAddress());
            logger.error("Log test");
        }
    }
}
