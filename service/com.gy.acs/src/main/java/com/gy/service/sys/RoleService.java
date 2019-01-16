package com.gy.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.vo.req.RoleQuery;
import com.gy.mapper.sys.RoleMapper;
import com.gy.domain.dto.sys.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 角色服务层
 * @作者: FangLin
 * @创建时间: 2018/11/16 11:28
 * @版本号: V1.0
 *  
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 添加角色
     * @param role
     * @return
     */
    public int addRole(Role role){
        return roleMapper.insertRole(role);
    }

    /**
     * 获取角色信息
     * @param roleId
     * @return
     */
    public Role getRole(String roleId){
        return roleMapper.selectRoleById(roleId);
    }


    /**
     * 获取角色分页列表信息
     * @param roleQuery
     * @return
     */
    public PageInfo<Role> listRolePage(RoleQuery roleQuery){
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getSize());
        List<Role> roleList =  roleMapper.selectListRolePage(roleQuery);
        return new PageInfo<>(roleList);
    }

    /**
     * 编辑角色信息
     * @param role
     * @return
     */
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 删除角色信息
     *
     * @param roleId
     * @return
     */
    public int deleteRole(String roleId) {
        return roleMapper.deleteRoleById(roleId);
    }
}
