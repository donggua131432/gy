package com.gy.domain.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 角色查询
 * @作者: DuKai
 * @创建时间: 2018/11/27 14:19
 * @版本号: V1.0
 */
@ApiModel(value = "RoleQuery",description = "角色查询实体")
@Data
public class RoleQuery extends PageCondition implements Serializable {
    private static final long serialVersionUID = -8636510160678675473L;

    @ApiModelProperty("角色Id")
    private String roleId;
    @ApiModelProperty(value="角色编码")
    private String roleCode;
    @ApiModelProperty(value="角色名称")
    private String roleName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
