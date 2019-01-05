package com.gy.controller;

import com.gy.enums.ErrorCodeEnum;
import com.gy.model.ReturnInfo;
import com.gy.model.sys.Menu;
import com.gy.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/11/16 18:00
 *  * @版本号: V1.0
 *  
 */

@RestController
@Api(tags = {"菜单接口"},description = "Menu-API")
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "新增菜单信息", notes = "新增一个惨淡信息")
    @RequestMapping("/addMenu")
    public Object addMenu(@ApiParam(name = "menu", value = "角色对象") Menu menu) {
        int flag = menuService.addMenu(menu);
        ErrorCodeEnum ee = flag>0?ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
