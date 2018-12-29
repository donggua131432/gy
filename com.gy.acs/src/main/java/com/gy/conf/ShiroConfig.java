package com.gy.conf;

import com.gy.core.shiro.UserRealm;
import com.gy.redis.GyRedisBean;
import com.gy.redis.GyRedisInfo;
import com.gy.redis.shiro.RedisCacheManager;
import com.gy.redis.shiro.RedisManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;


import java.util.*;

/**
 * @描述: Shiro配置
 * @作者: DuKai
 * @创建时间: 2018/7/23 10:07
 * @版本号: V3.0
 */
@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    private GyRedisBean gyRedisBean;

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明:
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        logger.info("Start==========进入shiroFilter拦截器");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        //过滤链定义，从上向下顺序执行，一般将 /**放在最为下边【这是一个坑呢，一不小心代码就不好使了】
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        //自定义加载权限资源关系

        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/cms/login", "anon");

        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager dwsm =  new DefaultWebSecurityManager();

        //设置realm.
        dwsm.setRealm(userRealm());
        // 自定义缓存实现 使用redis
        dwsm.setCacheManager(redisCacheManager());

        //停用shiro的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        dwsm.setSubjectDAO(subjectDAO);

        return dwsm;
    }

    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPoolConfig(initGenericObjectPoolConfig(gyRedisBean));
        GyRedisInfo redisInfo = gyRedisBean.getRedisInfos().stream().filter(v -> v.isDefault()).findFirst().get();
        redisManager.setHost(redisInfo.getHost());
        redisManager.setPort(redisInfo.getPort());
        redisManager.setTimeout(gyRedisBean.getTimeout());
        redisManager.setPassword(redisInfo.getAuthKey());
        return redisManager;
    }

    private JedisPoolConfig initGenericObjectPoolConfig(GyRedisBean config) {
        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setLifo(true);
        conf.setMaxIdle(config.getMaxIdle());
        conf.setMinIdle(config.getMinIdle());
        conf.setMaxTotal(config.getMaxActive());
        conf.setBlockWhenExhausted(false);
        conf.setTestOnBorrow(config.isTestOnBorrow());
        conf.setTestOnReturn(config.isTestOnReturn());
        conf.setTestOnCreate(true);
        conf.setMaxWaitMillis(config.getMaxWait());
        return conf;
    }

    /**
     * cacheManager 缓存redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setPrincipalIdFieldName("token");
        return redisCacheManager;
    }




}
