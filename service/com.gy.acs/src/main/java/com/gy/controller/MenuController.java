package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.core.util.TreeUtil;
import com.gy.domain.ResultPage;
import com.gy.domain.vo.req.MenuQuery;
import com.gy.domain.vo.res.MenuTreeNode;
import com.gy.enums.ErrorCodeEnum;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.Menu;
import com.gy.service.sys.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 菜单接口
 * @作者: FangLin
 * @创建时间: 2018/11/16 18:00
 * @版本号: V1.0
 *  
 */

@RestController
@Api(tags = {"菜单接口"},description = "Menu-API")
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "新增菜单信息", notes = "新增一个菜单信息")
    @PostMapping("/addMenu")
    public ReturnInfo addMenu(@ApiParam(name = "menu", value = "菜单对象") @RequestBody Menu menu) {
        int flag = menuService.addMenu(menu);
        ErrorCodeEnum ee = flag>0?ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "获取全部菜单列表", notes = "获取全部菜单列表")
    @GetMapping("/listAllMenu")
    public ReturnInfo<List<MenuTreeNode>> listMenu() {
        List<MenuTreeNode> treeNodes = new ArrayList<>();
        List<Menu> menuList = menuService.listAllMenu();
        for (Menu menu: menuList) {
            MenuTreeNode node = new MenuTreeNode();
            BeanUtils.copyProperties(menu, node);
            treeNodes.add(node);
        }
        List<MenuTreeNode> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes,"0");
        return new ReturnInfo<MenuTreeNode>().setResult(ErrorCodeEnum.SUCCESS).setData(menuTreeNodes);
    }

    @ApiOperation(value = "获取菜单分页列表", notes = "获取菜单分页列表")
    @PostMapping("/listMenuPage")
    public ReturnInfo<ResultPage<Menu>> listMenuPage(@ApiParam("菜单查询参数") @RequestBody MenuQuery menuQuery) {
        PageInfo<Menu> pageInfo = menuService.listMenuPage(menuQuery);
        ResultPage<Menu> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "更新菜单", notes = "更新菜单")
    @PutMapping("/updateMenu")
    public ReturnInfo updateMenu(@ApiParam(name = "menu", value = "菜单对象") @RequestBody Menu menu) {
        int flag = menuService.updateMenu(menu);
        ErrorCodeEnum ee = flag>0?ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @DeleteMapping("/deleteMenu/{menuId}")
    public ReturnInfo deleteMenu(@ApiParam(name = "menuId", value = "菜单ID") @PathVariable String menuId) {
        int flag = menuService.deleteMenu(menuId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS : ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "获取父菜单ID", notes = "获取父菜单ID")
    @GetMapping("/listMenuParentId/{menuId}")
    public ReturnInfo<String> listMenuParentId(@ApiParam(name = "menuId", value = "菜单ID") @PathVariable String menuId){
        List<String> parentIdList = menuService.listMenuParentId(menuId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(parentIdList);
    }
}
