package cn.denghanxi.data.mapper;

import cn.denghanxi.model.SysRole;
import cn.denghanxi.model.SysUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest extends BaseMapperTest {
    private static Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
    private static UserMapper userMapper;

    @BeforeAll
    public static void beforeAll2() {
        userMapper = getSqlSession().getMapper(UserMapper.class);
    }

    @AfterAll
    public static void afterAll() {
        getSqlSession().close();
    }

    @Test
    void testSelectById() {
        SysUser admin = userMapper.selectById(1L);
        logger.debug(admin.toString());
        assertEquals(admin.getUserName(), "admin");
        assertEquals(admin.getUserInfo(), "管理员");
    }

    @Test
    void selectAll() {
        List<SysUser> sysUsers = userMapper.selectAll();
        for (SysUser user : sysUsers) {
            logger.debug(user.toString());
        }
        assertEquals(2, sysUsers.size());
    }

    @Test
    void selectRolesByUserId() {
        List<SysRole> sysRoles = userMapper.selectRolesByUserId(1L);
        for (SysRole sysRole : sysRoles) {
            logger.debug(sysRole.toString());
            assertTrue(sysRole.getRoleName().equals("管理员") || sysRole.getRoleName().equals("普通用户"));
        }
    }

    @Test
    void insertUser() {
        SysUser preUser = new SysUser();
        preUser.setUserName("John");
        preUser.setUserPassword("132");
        preUser.setUserEmail("adjim123@163.com");
        preUser.setUserInfo("a no t user");
        preUser.setCreateTime(Date.from(Instant.now()));
        userMapper.insertUser(preUser);
        List<SysUser> users = userMapper.selectAll();
        assertEquals(users.size(), 3);
        SysUser getUser = userMapper.selectByName(preUser.getUserName());
        assertEquals(preUser.getUserName(), getUser.getUserName());
        assertEquals(preUser.getUserPassword(), getUser.getUserPassword());
        assertEquals(preUser.getUserEmail(), getUser.getUserEmail());
        assertEquals(preUser.getUserInfo(), getUser.getUserInfo());
        assertEquals(preUser.getHeadImg(), getUser.getHeadImg());
        assertNotNull(getUser.getCreateTime());
    }
}