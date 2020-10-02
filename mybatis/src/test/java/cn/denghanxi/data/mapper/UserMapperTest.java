package cn.denghanxi.data.mapper;

import cn.denghanxi.model.SysRole;
import cn.denghanxi.model.SysUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest extends BaseMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);
    private static UserMapper userMapper;

    @BeforeAll
    public static void beforeAll2() {
        userMapper = getSqlSession().getMapper(UserMapper.class);
    }

    @AfterAll
    public static void afterAll() {
//        getSqlSession().close();
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
        preUser.setUserName("Jack");
        preUser.setUserPassword("12334");
        preUser.setUserEmail("adjim123@163.com");
        preUser.setUserInfo("a no t user");
        preUser.setHeadImg(new byte[]{1, 2});
        preUser.setCreateTime(Date.from(Instant.now()));
        int affectRowNum = userMapper.insertUser(preUser);

        assertEquals(affectRowNum, 1);

        SysUser getUser = userMapper.selectByName(preUser.getUserName());
        // SysUser getUser = userMapper.selectById(1002L);
        assertEquals(preUser.getUserName(), getUser.getUserName());
        assertEquals(preUser.getUserPassword(), getUser.getUserPassword());
        assertEquals(preUser.getUserEmail(), getUser.getUserEmail());
        assertEquals(preUser.getUserInfo(), getUser.getUserInfo());
        assertEquals(preUser.getHeadImg()[0], getUser.getHeadImg()[0]);
        assertEquals(preUser.getHeadImg()[1], getUser.getHeadImg()[1]);
        assertNotNull(getUser.getCreateTime());
        logger.debug(getUser.toString());
    }

    @Test
    void insertUser2() {
        SysUser preUser = new SysUser();
        preUser.setUserName("John");
        preUser.setUserPassword("132");
        preUser.setUserEmail("adjim123@163.com");
        preUser.setUserInfo("a no t user");
        preUser.setHeadImg(new byte[]{1, 2});
        preUser.setCreateTime(Date.from(Instant.now()));
        int affectRowNum = userMapper.insertUser2(preUser);
        assertEquals(affectRowNum, 1);

        SysUser getUser = userMapper.selectById(preUser.getId());
        assertEquals(preUser.getId(), getUser.getId());
        assertEquals(preUser.getUserName(), getUser.getUserName());
        assertEquals(preUser.getUserPassword(), getUser.getUserPassword());
        assertEquals(preUser.getUserEmail(), getUser.getUserEmail());
        assertEquals(preUser.getUserInfo(), getUser.getUserInfo());
        assertEquals(preUser.getHeadImg()[0], getUser.getHeadImg()[0]);
        assertEquals(preUser.getHeadImg()[1], getUser.getHeadImg()[1]);
        assertNotNull(getUser.getCreateTime());
        logger.debug(getUser.toString());
    }

    @Test
    void insertUser3() {
        SysUser preUser = new SysUser();
        preUser.setUserName("John");
        preUser.setUserPassword("132");
        preUser.setUserEmail("adjim123@163.com");
        preUser.setUserInfo("a no t user");
        preUser.setHeadImg(new byte[]{1, 2});
        preUser.setCreateTime(Date.from(Instant.now()));
        int affectRowNum = userMapper.insertUser3(preUser);
        assertEquals(affectRowNum, 1);

        SysUser getUser = userMapper.selectById(preUser.getId());
        assertEquals(preUser.getId(), getUser.getId());
        assertEquals(preUser.getUserName(), getUser.getUserName());
        assertEquals(preUser.getUserPassword(), getUser.getUserPassword());
        assertEquals(preUser.getUserEmail(), getUser.getUserEmail());
        assertEquals(preUser.getUserInfo(), getUser.getUserInfo());
        assertEquals(preUser.getHeadImg()[0], getUser.getHeadImg()[0]);
        assertEquals(preUser.getHeadImg()[1], getUser.getHeadImg()[1]);
        assertNotNull(getUser.getCreateTime());
        logger.debug(getUser.toString());
    }

    @Test
    void updateById() throws SQLException {
        SysUser admin = userMapper.selectById(1L);
        assertEquals(admin.getUserName(), "admin");
        String nameBefore = admin.getUserName();
        String emailBefore = admin.getUserEmail();
        admin.setUserName("admin_test");
        admin.setUserEmail("admjin1231@163.com");
        int affectRowNum = userMapper.updateById(admin);
        assertEquals(affectRowNum, 1);
        SysUser adminTest = userMapper.selectById(1L);
        assertEquals(adminTest.getUserName(), "admin_test");
        //recoverData
        logger.debug("Auto commit: " +getSqlSession().getConnection().getAutoCommit());
//        getSqlSession().rollback();
//        getSqlSession().close();
        admin.setUserName(nameBefore);
        admin.setUserEmail(emailBefore);
        userMapper.updateById(admin);
    }

    @Test
    void deleteById() {
        SysUser sysUser = new SysUser();
        String name = "asdasd12";
        sysUser.setUserName(name);
        int affectRowNum = userMapper.insertUser2(sysUser);
        assertEquals(affectRowNum, 1);
        SysUser getUser = userMapper.selectById(sysUser.getId());
        assertEquals(name, getUser.getUserName());
        assertEquals(userMapper.deleteById(getUser), 1);
        assertNull(userMapper.selectById(sysUser.getId()));
    }

    @Test
    void selectRolesByUserIdAndRoleEnabled() {
        List<SysRole> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
        assertNotNull(roleList);
        assertTrue(roleList.size() > 0);
        for (SysRole role : roleList) {
            logger.debug(role.toString());
        }
    }

    @Test
    void testCache(){
        SysUser admin = userMapper.selectById(1L);
        assertEquals(admin.getUserName(), "admin");
        admin.setUserName("AAA");
        SysUser admin2 = userMapper.selectById(1L);
        assertEquals("AAA", admin2.getUserName());
        assertEquals(admin, admin2);
        //recover
        admin.setUserName("admin");
    }
}