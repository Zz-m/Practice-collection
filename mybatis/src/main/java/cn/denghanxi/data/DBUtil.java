package cn.denghanxi.data;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;

public class DBUtil {
    public static void initDB(SqlSessionFactory sqlSessionFactory) {
        ScriptRunner scriptRunner = new ScriptRunner(sqlSessionFactory.openSession().getConnection());
        scriptRunner.setAutoCommit(true);
        scriptRunner.setStopOnError(true);
        try {
            scriptRunner.runScript(Resources.getResourceAsReader("schema.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
