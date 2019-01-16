package com.gy.domain.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @描述: 菜单树节点
 * @作者: DuKai
 * @创建时间: 2018/11/19 16:29
 * @版本号: V1.0
 */
@ApiModel(value = "MenuTreeNode",description = "菜单树节点")
@Data
public class MenuTreeNode extends TreeNode {
    private static final long serialVersionUID = -3541403852044198303L;

    @ApiModelProperty("资源编码")
    private String menuCode;
    @ApiModelProperty("菜单名称")
    private String menuName;
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
