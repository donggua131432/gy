package com.gy.shiro.filter;



import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Iterator;

/**
 * @描述: ${DESCRIPTION}
 * @作者: DuKai
 * @创建时间: 2018/11/19 20:18
 * @版本号: V1.0
 */
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestPathMatchingFilterChainResolver.class);

    public RestPathMatchingFilterChainResolver() {
        super();
    }

    public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
        super(filterConfig);
    }

    /**
     * 重写filterChain匹配
     * @param request
     * @param response
     * @param originalChain
     * @return
     */
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        } else {
            String requestURI = this.getPathWithinApplication(request);
            Iterator var6 = filterChainManager.getChainNames().iterator();

            String pathPattern;
            boolean flag = true;
            String[] strings = null;
            do {
                if (!var6.hasNext()) {
                    return null;
                }
                pathPattern = (String)var6.next();
                strings = pathPattern.split("==");
                if (strings.length == 2) {
                    // 分割出url+httpMethod,判断httpMethod和request请求的method是否一致,不一致直接false
                    if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(strings[1].toUpperCase())) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                } else {
                    flag = false;
                }
                pathPattern = strings[0];
            } while(!this.pathMatches(pathPattern, requestURI) || flag);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  Utilizing corresponding filter chain...");
            }
            if (strings.length == 2) {
                pathPattern = pathPattern.concat("==").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
            }
            return filterChainManager.proxy(originalChain, pathPattern);
        }
    }

}
