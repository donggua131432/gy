package com.gy.mapper.sys;

import com.gy.domain.dto.sys.Role;
import com.gy.domain.vo.req.RoleQuery;
import com.gy.shiro.rule.RolePermRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteRoleById(String roleId);

    int insertRole(Role record);

    Role selectRoleById(String roleId);

    List<Role> selectListRolePage(RoleQuery roleQuery);

    List<RolePermRule> selectRoleRules();

    int updateRole(Role record);
}