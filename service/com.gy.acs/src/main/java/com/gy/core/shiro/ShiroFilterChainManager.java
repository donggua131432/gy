package com.gy.core.shiro;

import com.gy.service.sys.ShiroRulesService;
import com.gy.shiro.filter.RestPathMatchingFilterChainResolver;
import com.gy.shiro.rule.RolePermRule;
import com.gy.util.ApplicationContextUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述: Filter 管理器
 * @作者: DuKai
 * @创建时间: 2018/11/21 09:42
 * @版本号: V1.0
 */
@Component
public class ShiroFilterChainManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroFilterChainManager.class);

    @Autowired
    private ShiroRulesService shiroRulesService;

    /**
     * 初始化获取过滤链
     * @return
     */
    public Map<String,Filter> initGetFilters() {
        Map<String,Filter> filters = new LinkedHashMap<>();
        PasswordFilter passwordFilter = new PasswordFilter();
        filters.put("auth",passwordFilter);
        BJwtFilter jwtFilter = new BJwtFilter();
        filters.put("jwt",jwtFilter);
        return filters;
    }

    /**
     * 初始化获取过滤链规则
     * @return
     */
    public Map<String,String> initGetFilterChain() {
        Map<String,String> filterChain = new LinkedHashMap<>();
        // -------------anon 默认过滤器忽略的URL
        List<String> defalutAnon = Arrays.asList("/css/**","/js/**","/user/getDynamicSecretKey");
        defalutAnon.forEach(ignored -> filterChain.put(ignored,"anon"));
        // -------------auth 默认需要认证过滤器的URL 走auth--PasswordFilter
        List<String> defalutAuth = Arrays.asList("/user/login", "/user/register");
        defalutAuth.forEach(auth -> filterChain.put(auth,"auth"));
        // -------------dynamic 动态URL
        if (shiroRulesService != null) {
            List<RolePermRule> rolePermRules = shiroRulesService.loadRolePermRules();
            if (null != rolePermRules) {
                rolePermRules.forEach(rule -> {
                    StringBuilder Chain = rule.toFilterChain();
                    if (null != Chain) {
                        filterChain.putIfAbsent(rule.getUrl(),Chain.toString());
                    }
                });
            }
        }
        return filterChain;
    }


    /**
     * 动态重新加载过滤链规则
     */
    public void reloadFilterChain() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = ApplicationContextUtil.getBean(ShiroFilterFactoryBean.class);
        AbstractShiroFilter abstractShiroFilter = null;
        try {
            abstractShiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            RestPathMatchingFilterChainResolver filterChainResolver = (RestPathMatchingFilterChainResolver) abstractShiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterChainManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            filterChainManager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(this.initGetFilterChain());
            shiroFilterFactoryBean.getFilterChainDefinitionMap().forEach((k, v) -> filterChainManager.createChain(k, v));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
