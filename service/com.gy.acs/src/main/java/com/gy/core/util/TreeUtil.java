package com.gy.core.util;


import com.gy.domain.vo.res.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 树结构相关工具类
 * @作者: DuKai
 * @创建时间: 2018/11/19 16:29
 * @版本号: V1.0
 */
public class TreeUtil {

    /**
     *  用双重循环建树
     * @param treeNodes 树节点列表
     * @param root root根标志
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> buildTreeBy2Loop(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T node : treeNodes) {
            if (root.equals(node.getParentId())) {
                trees.add(node);
            }

            for (T treeNode : treeNodes) {
                if (node.getMenuId().equals(treeNode.getParentId())) {
                    if (node.getChildren()==null) {
                        node.setChildren(new ArrayList<>());
                    }
                    node.addChilren(treeNode);
                }
            }
        }
        return trees;
    }

}
