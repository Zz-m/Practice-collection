package cn.denghanxi.data.mapper;

import cn.denghanxi.model.SysRole;
import cn.denghanxi.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);
    List<SysUser> selectAll();
    List<SysRole> selectRolesByUserId(Long userId);
}
