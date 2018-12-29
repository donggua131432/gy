package com.gy.conf;

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
        mapperScannerConfigurer.setBasePackage("com.gy.mapper,com.gy.mapper.*,com.gy.*.mapper");
        return mapperScannerConfigurer;
    }

    @Bean(name = "gyRedisBean")
    public GyRedisBean getRedisBean() {
        return new GyRedisBean();
    }


    @Bean(name="gyRedisFactory")
    public Object initRedisFactory(@Autowired GyRedisBean bean ) {
        GyRedisFactory factory = GyRedisFactory.getInstance();
        factory.init(bean);
        return factory;
    }
}
