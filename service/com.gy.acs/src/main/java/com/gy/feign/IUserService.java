package com.gy.feign;


import com.gy.domain.dto.sys.User;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IUserService {

    @RequestMapping("/getUser")
    User getUser(String username);
}
