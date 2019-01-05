package com.gy.service;

import com.gy.mapper.sys.RoleMapper;
import com.gy.model.sys.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/11/16 11:28
 *  * @版本号: V1.0
 *  
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    public int addRole(Role role){
        return roleMapper.insertRole(role);
    }

    public Role getRole(String roleId){
        return roleMapper.selectRoleById(roleId);
    }
}
