package com.gy.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class TsApplication {
    private final static Logger logger = LoggerFactory.getLogger(TsApplication.class);
    public static void main(String[] args) {
        //SpringApplication.run(AdvertClientApplication.class, args);
        new SpringApplicationBuilder(TsApplication.class).bannerMode(Banner.Mode.CONSOLE).run(args);
        logger.info("TsApplication Start Success");
    }
}
