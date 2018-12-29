package com.gy.conf;

import com.gy.feign.fallback.OrderServiceFallBack;
import com.gy.feign.fallback.OrderServiceFallbackFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Function:创建bean
 *
 * @author crossoverJie
 *         Date: 2017/9/3 21:55
 * @since JDK 1.8
 */
@Configuration
public class OrderConfig {
    //// FIXME: 26/04/2018 为了方便测试，先把降级关掉
    //@Bean
    public OrderServiceFallBack fallBack(){
        return new OrderServiceFallBack();
    }
    //// FIXME: 26/04/2018 为了方便测试，先把降级关掉
    //@Bean
    public OrderServiceFallbackFactory factory(){
        return new OrderServiceFallbackFactory();
    }

}
