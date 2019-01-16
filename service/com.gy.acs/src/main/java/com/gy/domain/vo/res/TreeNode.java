package com.gy.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述: tree抽象节点 树可继承这个节点
 * @作者: DuKai
 * @创建时间: 2018/11/19 16:29
 * @版本号: V1.0
 */
@ApiModel(value = "TreeNode",description = "菜单树子节点")
@Data
public abstract class TreeNode implements Serializable {
    private static final long serialVersionUID = -2741556821713176149L;

    @ApiModelProperty("菜单Id")
    protected String menuId;
    @ApiModelProperty("菜单父ID")
    protected String parentId;
    @ApiModelProperty("菜单树子节点列表")
    protected List<TreeNode> children;

    public void addChilren(TreeNode node) {
        this.children.add(node);
    }
}
