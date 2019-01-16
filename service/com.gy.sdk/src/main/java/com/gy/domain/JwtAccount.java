package com.gy.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: ${DESCRIPTION}
 * @作者: DuKai
 * @创建时间: 2018/11/20 14:18
 * @版本号: V1.0
 */
@Data
public class JwtAccount implements Serializable {

    private static final long serialVersionUID = -895875540581785581L;

    private String tokenId;// 令牌id
    private String appId;// 客户标识（用户名、账号）
    private String issuer;// 签发者(JWT令牌此项有值)
    private Date issuedAt;// 签发时间
    private String audience;// 接收方(JWT令牌此项有值)
    private String roles;// 访问主张-角色(JWT令牌此项有值)
    private String perms;// 访问主张-资源(JWT令牌此项有值)
    private String host;// 客户地址

    public JwtAccount() {

    }

    public JwtAccount(String tokenId, String appId, String issuer, Date issuedAt, String audience, String roles, String perms, String host) {
        this.tokenId = tokenId;
        this.appId = appId;
        this.issuer = issuer;
        this.issuedAt = issuedAt;
        this.audience = audience;
        this.roles = roles;
        this.perms = perms;
        this.host = host;
    }
}
