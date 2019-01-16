package com.gy.shiro.rule;


import com.gy.util.JsonWebTokenUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * @描述: ${DESCRIPTION}
 * @作者: DuKai
 * @创建时间: 2018/11/19 20:18
 * @版本号: V1.0
 */
public class RolePermRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url; //资源URL
    private String needRoles; //访问资源所需要的角色列表，多个列表用逗号间隔

    public String getUrl() {
        return url;
    }

    public String getNeedRoles() {
        return needRoles;
    }

    /**
     * 将url needRoles转化成shiro可识别的过滤器链：url=jwt[角色1、角色2、角色n]
     * @return
     */
    public StringBuilder toFilterChain() {
        if (null == this.url || this.url.isEmpty()) return null;
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> setRole = JsonWebTokenUtil.split(this.getNeedRoles());
        //约定若role_anon角色拥有此url资源的权限,则此url资源直接访问不需要认证和权限
        if (!StringUtils.isEmpty(this.getNeedRoles()) && setRole.contains("role_anon")) {
            stringBuilder.append("anon");
        }
        //其他自定义资源url需通过jwt认证和角色认证
        if (!StringUtils.isEmpty(this.getNeedRoles()) && !setRole.contains("role_anon")) {
            stringBuilder.append("jwt"+"["+this.getNeedRoles()+"]");
        }
        return stringBuilder.length() > 0 ? stringBuilder : null;
    }

    public String toString() {
        return "RolePermRule [url="+url+", needRoles="+needRoles+"]";
    }
}
