package com.gy.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 使用@EnableFeignClients开启Feign
 */

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(
    scanBasePackages = {
        "com.gy.controller",
        "com.gy.feign",
        "com.gy.fallback",
        "com.gy.conf",
        "com.gy.service"
    }, exclude = {

        RedisAutoConfiguration.class,
        DataSourceAutoConfiguration.class
    }
)
@EnableWebMvc
public class AcsApplication {
    private final static Logger logger = LoggerFactory.getLogger(AcsApplication.class);
    public static void main(String[] args) {
        new SpringApplicationBuilder(AcsApplication.class).bannerMode(Banner.Mode.CONSOLE).run(args);
        logger.info("AcsApplication Start Success");
    }


}
