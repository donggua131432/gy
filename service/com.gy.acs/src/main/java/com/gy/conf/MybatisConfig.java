package com.gy.conf;

import com.gy.oss.OssConfig;
import com.gy.oss.OssUtil;
import com.gy.redis.GyRedisBean;
import com.gy.redis.GyRedisFactory;
import com.gy.util.ApplicationContextUtil;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @描述: Mybatis配置
 * @作者: DuKai
 * @创建时间: 2018/7/23 10:07
 * @版本号: V3.0
 */
@Configuration
@AutoConfigureAfter(value = { MultiDataSourceConfig.class })
@EnableCaching
public class MybatisConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.setApplicationContext(applicationContext);
    }

    @Bean
    public MapperScannerConfigurer basicMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage("com.gy.mapper,com.gy.mapper.*");
        return mapperScannerConfigurer;
    }

    @Bean(name = "gyRedisBean")
    public GyRedisBean getRedisBean() {
        return new GyRedisBean();
    }


    @Bean(name = "gyRedisFactory")
    public Object initRedisFactory(@Autowired GyRedisBean bean) {
        GyRedisFactory factory = GyRedisFactory.getInstance();
        factory.init(bean);
        return factory;
    }

    @Bean(name = "getOSSConfig")
    public OssConfig getOSSConfig() {
        return new OssConfig();
    }

    @Bean(name = "initOssUtil")
    public Object initOssUtil(@Autowired OssConfig ossConfig) {
        OssUtil ossUtil = OssUtil.getInstance();
        ossUtil.init(ossConfig);
        return ossUtil;
    }


    @Bean(name="gySysConfig")
    public GySysConfig getGySysConfig() {
        return new GySysConfig();
    }

    /**
     * 文件上传大小设置
     * @return
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(104857600);
        multipartResolver.setMaxInMemorySize(4096);
        return multipartResolver;
    }
}


