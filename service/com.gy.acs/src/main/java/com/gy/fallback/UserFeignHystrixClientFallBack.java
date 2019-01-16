package com.gy.fallback;

import com.gy.feign.UserFeignHystrixClient;
import com.gy.domain.dto.sys.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignHystrixClientFallBack implements UserFeignHystrixClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFeignHystrixClientFallBack.class);

    /**
     * hystrix fallback方法
     * @param id id
     * @return 默认的用户
     */
    @Override
    public User findByIdFeign(Long id) {
        UserFeignHystrixClientFallBack.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
        User user = makeUser();
        return user;
    }

    @Override
    public User testTimeOutFeign(Long time) {
        UserFeignHystrixClientFallBack.LOGGER.info("异常发生，进入fallback方法，接收的参数：time = {}", time);
        User user = makeUser();
        return user;
    }

    private User makeUser() {
        User user = new User();
        user.setUserId("");
        user.setUpdateUserName("default username");
        user.setEmail("fanglin@sina.cn");
        return user;
    }
}
