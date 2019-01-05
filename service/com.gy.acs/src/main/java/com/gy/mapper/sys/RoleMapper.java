package com.gy.mapper.sys;

import com.gy.model.sys.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int deleteRoleById(String roleId);

    int insertRole(Role record);

    Role selectRoleById(@Param("roleId") String roleId);

    int updateRole(Role record);
}