package com.gy.controller;

import com.gy.feign.UserFeignHystrixClient;

import com.gy.model.sys.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "RPC-用户信息接口", description = "UserFeign-API")
public class UserFeignController {

    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @GetMapping("feign/{id}")
    public User findByIdFeign(@PathVariable Long id) {
        User user = this.userFeignHystrixClient.findByIdFeign(id);
        return user;
    }

    @GetMapping("feign/time_out/{time}")
    public User findUserTimeOutTest(@PathVariable Long time) {
        User user = this.userFeignHystrixClient.testTimeOutFeign(time);
        return user;
    }

}
