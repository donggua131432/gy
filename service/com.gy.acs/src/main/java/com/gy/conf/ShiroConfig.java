package com.gy.conf;

import com.gy.core.shiro.PasswordRealm;
import com.gy.core.shiro.ShiroFilterChainManager;
import com.gy.shiro.filter.ASubjectFactory;
import com.gy.shiro.filter.RestShiroFilterFactoryBean;
import com.gy.shiro.matcher.JwtMatcher;
import com.gy.shiro.matcher.PasswordMatcher;
import com.gy.shiro.realm.AModularRealmAuthenticator;
import com.gy.shiro.realm.JwtRealm;
import com.gy.shiro.token.JwtToken;
import com.gy.shiro.token.PasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
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

import java.util.*;

/**
 * @描述: shiro配置
 * @作者: DuKai
 * @创建时间: 2018/11/20 13:45
 * @版本号: V1.0
 */

@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    private PasswordMatcher passwordMatcher;

    @Autowired
    private JwtMatcher jwtMatcher;


    /**
     * 获取对shiro bean生命周期的管理实例
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 配置shiroFilter拦截器
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     * @param securityManager
     * @param filterChainManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, ShiroFilterChainManager filterChainManager) {
        logger.info("Start==========进入shiroFilter拦截器");
        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置过滤器
        shiroFilterFactoryBean.setFilters(filterChainManager.initGetFilters());
        // 设置过滤规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainManager.initGetFilterChain());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        // 设置授权
        dwsm.setAuthenticator(new AModularRealmAuthenticator());

        Collection<Realm> realms = new ArrayList<>();
        realms.add(passwordRealm());
        realms.add(jwtRealm());
        dwsm.setRealms(realms);

        //停用shiro的session
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) dwsm.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        ASubjectFactory subjectFactory = new ASubjectFactory(evaluator);
        dwsm.setSubjectFactory(subjectFactory);

        SecurityUtils.setSecurityManager(dwsm);
        return dwsm;
    }

    /**
     * password
     * @return
     */
    @Bean
    public PasswordRealm passwordRealm(){
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setCredentialsMatcher(passwordMatcher);
        passwordRealm.setAuthenticationTokenClass(PasswordToken.class);
        return passwordRealm;
    }

    /**
     * jwt
     * @return
     */
    @Bean
    public JwtRealm jwtRealm(){
        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        return jwtRealm;
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
}
