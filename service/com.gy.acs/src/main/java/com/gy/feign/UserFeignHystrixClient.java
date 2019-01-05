package com.gy.feign;


import com.gy.fallback.UserFeignHystrixClientFallBack;
import com.gy.model.sys.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "acs", fallback = UserFeignHystrixClientFallBack.class)
public interface UserFeignHystrixClient {


    @RequestMapping("/{id}")
    User findByIdFeign(@PathVariable("id") Long id);

    @RequestMapping("/time_out/{time}")
    User testTimeOutFeign(@PathVariable("time") Long time);
}
