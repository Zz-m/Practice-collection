package cn.denghanxi.data.mapper;

import cn.denghanxi.data.DBUtil;
import cn.denghanxi.model.Country;
import cn.denghanxi.model.SysUser;
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

class UserMapperTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
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
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser admin = userMapper.selectById(1L);
            logger.debug(admin.toString());
            assertEquals(admin.getUserName(), "admin");
            assertEquals(admin.getUserInfo(), "管理员");
        }
    }
}