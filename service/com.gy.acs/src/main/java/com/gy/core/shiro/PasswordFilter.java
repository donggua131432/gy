package com.gy.core.shiro;

import com.gy.domain.ReturnInfo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.redis.RedisService;
import com.gy.shiro.token.PasswordToken;
import com.gy.util.IpUtil;
import com.gy.util.JsonUtil;
import com.gy.util.RequestResponseUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述: 基于用户名密码的认证过滤器
 * @作者: DuKai
 * @创建时间: 2018/11/20 17:42
 * @版本号: V1.0
 */
public class PasswordFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(PasswordFilter.class);


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request,response);
        // 如果其已经登录，在此发送登录请求
        if(null != subject && subject.isAuthenticated()){
            return true;
        }
        //处理options问题
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
            logger.info(httpServletRequest.getHeader("Authorization"));
        }

        //拒绝，统一交给 onAccessDenied处理
        return false;
    }

    @Override
    protected boolean onAccessDenied(@RequestBody ServletRequest servletRequest, ServletResponse servletResponse){
        HttpServletRequest request =  (HttpServletRequest) servletRequest;
        logger.info("=========请求地址："+request.getRequestURI()+"===========getMethod:"+request.getMethod());;
        // 判断若为获取登录注册加密动态秘钥请求
        String urlStr = request.getRequestURI();
        // 判断是否是登录请求
        if("/user/login".equals(urlStr)){
            AuthenticationToken authenticationToken = createPasswordToken(request);
            Subject subject = getSubject(servletRequest,servletResponse);
            try {
                subject.login(authenticationToken);
                //登录认证成功,进入请求派发json web token url资源内
                return true;
            }catch (AuthenticationException e) {
                logger.warn(authenticationToken.getPrincipal()+"::"+e.getMessage());
                // 返回response告诉客户端认证失败
                RequestResponseUtil.responseWrite(
                        JsonUtil.objectToString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.LOGIN_FAILED))
                        ,servletResponse);
                return false;
            }catch (Exception e) {
                logger.error(authenticationToken.getPrincipal()+"::认证异常::"+e.getMessage(),e);
                // 返回response告诉客户端认证失败
                RequestResponseUtil.responseWrite(
                        JsonUtil.objectToString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.LOGIN_FAILED))
                        ,servletResponse);
                return false;
            }
        }
        // 判断是否为注册请求,若是通过过滤链进入controller注册
        if ("/user/register".equals(urlStr)) {
            return true;
        }
        // 之后添加对账户的找回等

        // response 告知无效请求
        RequestResponseUtil.responseWrite(
                JsonUtil.objectToString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.REQUEST_INVALID))
                ,servletResponse);
        return false;
    }

    private AuthenticationToken createPasswordToken(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        String timestamp = request.getParameter("timestamp");
        String password = request.getParameter("password");
        String host = IpUtil.getIpFromRequest(WebUtils.toHttp(request));
        String userKey = request.getParameter("userKey");
        String tokenKey = RedisService.getRedisService().get("TOKEN_KEY_"+host.toUpperCase()+userKey);
        return new PasswordToken(appId,password,timestamp,host,tokenKey);
    }
}
