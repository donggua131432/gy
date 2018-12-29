package com.gy.model.sys;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("系统菜单信息")
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 3538730615510444661L;

    @ApiModelProperty("菜单Id")
    private Integer menuId;
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单父ID")
    private Integer parentId;
    @ApiModelProperty("url地址")
    private String url;
    @ApiModelProperty("菜单类型【menu_type】【0：正常 1：禁用】")
    private String type;
    @ApiModelProperty("菜单图标")
    private String menuIco;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}