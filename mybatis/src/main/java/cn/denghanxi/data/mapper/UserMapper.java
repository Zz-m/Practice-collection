package cn.denghanxi.data.mapper;

import cn.denghanxi.model.SysRole;
import cn.denghanxi.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);
    SysUser selectByName(String name);
    List<SysUser> selectAll();
    List<SysRole> selectRolesByUserId(Long userId);
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") int enabled);
    int insertUser(SysUser sysUser);
    int insertUser2(SysUser sysUser);
    int insertUser3(SysUser sysUser);
    int updateById(SysUser sysUser);
    int deleteById(SysUser sysUser);
}
