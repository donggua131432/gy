package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.vo.req.RoleQuery;
import com.gy.enums.ErrorCodeEnum;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.Role;
import com.gy.service.sys.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 角色接口
 * @作者: FangLin
 * @创建时间: 2018/11/16 10:38
 * @版本号: V1.0
 *  
 */
@RestController
@Api(tags={"角色信息接口"}, description = "Role-API")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表信息" , notes = "获取角色分页列表信息")
    @PostMapping("/listRolePage")
    public ReturnInfo<ResultPage<Role>> listRolePage(@ApiParam("角色查询条件") @RequestBody RoleQuery roleQuery){
        PageInfo<Role> pageInfo = roleService.listRolePage(roleQuery);
        ResultPage<Role> resultPage = ResultPage.getInstance()
                .setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }


    @ApiOperation(value = "获取角色信息", notes="获取角色信息")
    @GetMapping("/getRole/{roleId}")
    public ReturnInfo<Role> getRole(@ApiParam(value="角色ID",required=true) @PathVariable String roleId) {
        System.out.println("根据角色ID获取角色信息");
        Role role = roleService.getRole(roleId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(role);
    }


    @ApiOperation(value = "新增角色", notes="新增一个角色信息")
    @PostMapping("/addRole")
    public ReturnInfo addRole(@ApiParam(name="role",value="角色对象",required = true) @RequestBody Role role){
        int flag = roleService.addRole(role);
        ErrorCodeEnum ee = flag > 0 ?  ErrorCodeEnum.SUCCESS : ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "编辑角色信息", notes = "编辑角色信息")
    @PutMapping("/updateRole")
    public ReturnInfo updateRole(@ApiParam(name = "role", value = "角色对象", required = true)@RequestBody Role role) {
        int flag = roleService.updateRole(role);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS : ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @DeleteMapping("/deleteRole/{roleId}")
    public ReturnInfo deleteRole(@ApiParam(name = "roleID", value = "角色ID", required = true) @PathVariable String roleId) {
        int flag = roleService.deleteRole(roleId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS : ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
