package com.gy.core.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 用户认证
 * @作者: DuKai
 * @创建时间: 2018/11/15 17:12
 * @版本号: V1.0
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    /**
     * User授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始User权限授权");

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        //权限组
        List<String> groupNames=new ArrayList<>();
        //权限
        List<String> permissions=new ArrayList<>();


        info.addRoles(groupNames);
        info.addStringPermissions(permissions);

        return info;
    }

    /**
     * CustUser认证
     * @param userInfoToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken userInfoToken) throws AuthenticationException {
        log.info("开始User身份认证..");
        return null;
    }
}
