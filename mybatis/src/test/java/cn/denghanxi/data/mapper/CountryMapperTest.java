package cn.denghanxi.data.mapper;

import cn.denghanxi.CreateDatabaseTest;
import cn.denghanxi.data.DBUtil;
import cn.denghanxi.model.Country;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryMapperTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Logger logger = LoggerFactory.getLogger(CountryMapperTest.class);
    @BeforeAll
    static void beforeAll() throws IOException {
        String resourcePath = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        DBUtil.initDB(sqlSessionFactory);
    }

    @Test
    void simpleTest(){
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            List<Country> countryList = sqlSession.selectList("selectAll");
            for (Country country : countryList) {
                System.out.println(country);
            }
            assertEquals(countryList.size(), 5);
        }
    }
}