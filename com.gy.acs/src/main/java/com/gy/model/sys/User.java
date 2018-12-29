package com.gy.model.sys;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("用户信息")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8127073458986330782L;

    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("盐")
    private String salt;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机")
    private String mobile;
    @ApiModelProperty("状态【user_status(0：正常 1：禁用)】")
    private String status;
    @ApiModelProperty("部门ID")
    private String deptId;
    @ApiModelProperty("用户标识")
    private String token;
    @ApiModelProperty("操作用户ID")
    private String updateUserId;
    @ApiModelProperty("操作用户名")
    private String updateUserName;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creteTime;
    @ApiModelProperty("更新时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}