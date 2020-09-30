package cn.denghanxi.data.mapper;

import cn.denghanxi.data.DBUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;

public class BaseMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    static void baseBeforeAll() throws IOException {
        String resourcePath = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resourcePath);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        DBUtil.initDB(sqlSessionFactory);
    }

    static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
