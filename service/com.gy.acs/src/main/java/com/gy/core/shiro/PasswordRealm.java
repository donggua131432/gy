package com.gy.core.shiro;


import com.gy.domain.Account;
import com.gy.service.sys.UserService;
import com.gy.shiro.token.PasswordToken;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @描述: 登录认证realm
 * @作者: DuKai
 * @创建时间: 2018/11/21 11:18
 * @版本号: V1.0
 */
public class PasswordRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(PasswordRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 此Realm只支持PasswordToken
     * @return
     */
    public Class<?> getAuthenticationTokenClass() {
        return PasswordToken.class;
    }
    /**
     * 登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (!(authenticationToken instanceof PasswordToken)) return null;
        if(null==authenticationToken.getPrincipal()||null==authenticationToken.getCredentials()){
            throw new UnknownAccountException();
        }
        PasswordToken passwordToken = (PasswordToken) authenticationToken;

        String appId = (String)authenticationToken.getPrincipal();
        Account account = userService.loadAccount(appId);
        if (account != null) {
            // 用盐对密码进行MD5加密
            String passMd5Str = new SimpleHash("MD5",
                    passwordToken.getPassword(),
                    ByteSource.Util.bytes(account.getSalt()),2).toHex();
            passwordToken.setPassword(passMd5Str);
            return new SimpleAuthenticationInfo(appId,account.getPassword(),getName());
        } else {
            return new SimpleAuthenticationInfo(appId,"",getName());
        }
    }

    /**
     * 登录成功之后派发json web token授权在那里进行
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
