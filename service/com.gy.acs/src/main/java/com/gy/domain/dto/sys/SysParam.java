package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "SysParam",description = "系统参数")
@Data
public class SysParam implements Serializable {
    private static final long serialVersionUID = 7198158707745770004L;

    @ApiModelProperty("参数ID")
    private String paramId;
    @ApiModelProperty("参数名称")
    private String paramName;
    @ApiModelProperty("参数值")
    private String paramValue;
    @ApiModelProperty("所属系统")
    private String sysBelong;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("是否删除【0：否 1：是】")
    private String delStatus;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("最后更新人名称")
    private String updateUser;
    @ApiModelProperty("最后更新人ID")
    private String updateUserId;
    @ApiModelProperty("最后更新时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}