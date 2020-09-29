package cn.denghanxi.data.mapper;

import cn.denghanxi.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryMapperTest extends BaseMapperTest {
    private static Logger logger = LoggerFactory.getLogger(CountryMapperTest.class);

    @Test
    void simpleTest() {
        try (SqlSession sqlSession = getSqlSession()) {
            List<Country> countryList = sqlSession.selectList("cn.denghanxi.data.mapper.CountryMapper.selectAll");
            for (Country country : countryList) {
                System.out.println(country);
            }
            assertEquals(countryList.size(), 5);
        }
    }
}