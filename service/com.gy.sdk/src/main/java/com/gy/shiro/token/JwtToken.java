package com.gy.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @描述: ${DESCRIPTION}
 * @作者: DuKai
 * @创建时间: 2018/11/19 20:18
 * @版本号: V1.0
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String appId;         //用户的标识
    private String ipHost;        //用户的IP
    private String deviceInfo;    //设备信息
    private String jwt;           //json web token值

    public JwtToken(String ipHost, String deviceInfo, String jwt,String appId) {
        this.ipHost = ipHost;
        this.deviceInfo = deviceInfo;
        this.jwt = jwt;
        this.appId = appId;
    }

    public Object getPrincipal() {
        return this.appId;
    }

    public Object getCredentials() {
        return this.jwt;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
