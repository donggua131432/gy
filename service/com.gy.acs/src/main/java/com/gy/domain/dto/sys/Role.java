package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Role",description = "角色信息")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 4035757728763425819L;

    @ApiModelProperty("角色Id")
    private String roleId;
    @ApiModelProperty(value="角色编码",required=true)
    private String roleCode;
    @ApiModelProperty(value="角色名称",required=true)
    private String roleName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}