package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "Menu",description = "系统菜单信息")
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 3538730615510444661L;

    @ApiModelProperty("菜单Id")
    private String menuId;
    @ApiModelProperty("资源编码")
    private String menuCode;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单父ID")
    private String parentId;
    @ApiModelProperty("url地址")
    private String url;
    @ApiModelProperty("菜单类型【menu_type】【1:菜单menu 2:资源element(rest-api) 3:资源分类】")
    private String type;
    @ApiModelProperty("访问方法")
    private String method;
    @ApiModelProperty("菜单图标")
    private String menuIco;
    @ApiModelProperty("状态(0:正常 1:禁用)")
    private String status;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("修改时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}