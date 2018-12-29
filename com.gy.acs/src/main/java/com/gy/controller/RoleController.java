package com.gy.controller;

import com.gy.enums.ErrorCodeEnum;
import com.gy.model.ReturnInfo;
import com.gy.model.sys.Role;
import com.gy.model.sys.User;
import com.gy.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  * @描述: 角色
 *  * @作者: FangLin
 *  * @创建时间: 2018/11/16 10:38
 *  * @版本号: V1.0
 *  
 */
@RestController
@Api(tags={"角色信息接口"}, description = "Role-API")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "获取角色列表信息" , notes = "根据url的id来获取用户详细信息，返回List<Role>类型用户信息的JSON")
    @GetMapping("/listRole")
    public Object listRole(){
        List<Role> roles = new ArrayList<>();
        Role role =  new Role();
        role.setRoleId("11111");
        role.setRoleName("zhangsan");
        roles.add(role);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(roles);
    }


    @ApiOperation(value = "获取角色信息", notes="获取角色信息")
    @GetMapping("/getRole/{roleId}")
    public Object getRole(@ApiParam(value="角色ID",required=true) @PathVariable String roleId) {
        System.out.println("根据角色ID获取角色信息");
        Role role = roleService.getRole(roleId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(role);
    }


    @ApiOperation(value = "新增角色", notes="新增一个角色信息")
    @PostMapping("/addRole")
    public Object addRole(@ApiParam(name="role",value="角色对象",required=true) Role role){
        int flag = roleService.addRole(role);
        ErrorCodeEnum ee = flag > 0 ?  ErrorCodeEnum.SUCCESS : ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
