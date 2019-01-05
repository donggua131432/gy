package com.gy.controller;

import com.gy.model.sys.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"用户信息接口"}, description = "User-API")
@RequestMapping("/user")
public class UserController {


    @ApiOperation(value = "获取用户信息", notes="获取用户信息")
    @GetMapping("/getUser")
    public User getUser() {
        User user = new User();
        user.setUserId("11111111");
        user.setUserName("张三");
        user.setMobile("18617521111");

        System.out.println("aaaaaaaaaaaaaaaaa");
        return user;
    }
}
