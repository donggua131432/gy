package com.gy.service.sys;

import com.gy.mapper.sys.RoleMapper;
import com.gy.shiro.rule.RolePermRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 动态过滤规则提供者服务
 * @作者: DuKai
 * @创建时间: 2018/11/20 14:18
 * @版本号: V1.0
 */
@Service
public class ShiroRulesService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 加载基于角色/资源的过滤规则
     * 即：用户-角色-资源（URL），对应关系存储与数据库中
     * 在shiro中生成的过滤器链为：url=jwt[角色1、角色2、角色n]
     * @return
     */
    public List<RolePermRule> loadRolePermRules() {
        return roleMapper.selectRoleRules();
    }

}
