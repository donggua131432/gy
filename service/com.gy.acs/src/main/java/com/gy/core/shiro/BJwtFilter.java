package com.gy.core.shiro;

import com.alibaba.fastjson.JSON;
import com.gy.constants.RedisKeyConstants;
import com.gy.domain.ReturnInfo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.redis.RedisService;
import com.gy.service.sys.UserService;
import com.gy.shiro.filter.BPathMatchingFilter;

import com.gy.shiro.token.JwtToken;
import com.gy.util.IpUtil;
import com.gy.util.JsonWebTokenUtil;
import com.gy.util.RequestResponseUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @描述: 支持restful url 的过滤链  JWT json web token 过滤器，无状态验证
 * @作者: DuKai
 * @创建时间: 2018/11/21 09:42
 * @版本号: V1.0
 */
public class BJwtFilter extends BPathMatchingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BJwtFilter.class);

    @Autowired
    private UserService userService;

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue){
        Subject subject = getSubject(servletRequest,servletResponse);

        // 判断是否为JWT认证请求
        if ((null != subject && !subject.isAuthenticated()) && isJwtSubmission(servletRequest)) {
            AuthenticationToken token = createJwtToken(servletRequest);
            try {
                subject.login(token);
                return this.checkRoles(subject,mappedValue);
            }catch (AuthenticationException e) {

                // 如果是JWT过期
                if ("expiredJwt".equals(e.getMessage())) {
                    // 这里初始方案先抛出令牌过期，之后设计为在Redis中查询当前appId对应令牌，其设置的过期时间是JWT的两倍，此作为JWT的JWT_TTL时间
                    // 当JWT的有效时间过期后，查询其refresh时间，refresh时间有效即重新派发新的JWT给客户端，
                    // refresh也过期则告知客户端JWT时间过期重新认证

                    // 当存储在redis的JWT没有过期，即JWT_TTL没有过期
                    String appId = WebUtils.toHttp(servletRequest).getHeader("AppId");
                    String jwtToken = WebUtils.toHttp(servletRequest).getHeader("Authorization");
                    String refreshJwt = RedisService.getRedisService().get("JWT-SESSION-"+appId);
                    if (null != refreshJwt && refreshJwt.equals(jwtToken)) {
                        // 重新申请新的JWT
                        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
                        String roles = userService.loadAccountRole(appId);
                        String newJwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(),appId,
                                "asc-server", (long) (RedisKeyConstants.JWT_TTL >> 1), roles,null, SignatureAlgorithm.HS512);
                        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
                        RedisService.getRedisService().putObject("JWT-SESSION-"+appId,newJwt,RedisKeyConstants.JWT_TTL);
                        Map<String, String> map = new HashMap<>();
                        map.put("jwt",newJwt);
                        RequestResponseUtil.responseWrite(
                                JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(map)),
                                servletResponse);
                        return false;
                    }else {
                        // jwt时间失效过期,jwt refresh time失效 返回jwt过期客户端重新登录
                        RequestResponseUtil.responseWrite(
                                JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.JWT_EXPIRE)),
                                servletResponse);
                        return false;
                    }
                }
                // 其他的判断为JWT错误无效
                RequestResponseUtil.responseWrite(
                        JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.JWT_INVALID)),
                        servletResponse);
                return false;
            }catch (Exception e) {
                // 其他错误
                LOGGER.error(IpUtil.getIpFromRequest(WebUtils.toHttp(servletRequest))+"--JWT认证失败"+e.getMessage(),e);
                // 告知客户端JWT错误,需重新登录申请jwt
                RequestResponseUtil.responseWrite(
                        JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.JWT_INVALID))
                        ,servletResponse);
                return false;
            }
        }else {
            // 请求未携带jwt
            RequestResponseUtil.responseWrite(
                    JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.JWT_REQUEST_INVALID)),
                    servletResponse);
            return false;
        }
    }

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse){
        Subject subject = getSubject(servletRequest,servletResponse);
        // 未认证的情况
        if (null == subject || !subject.isAuthenticated()) {
            // 告知客户端JWT认证失败需跳转到登录页面
            RequestResponseUtil.responseWrite(
                    JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.JWT_INVALID)),
                    servletResponse);
        }else {
            // 已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            RequestResponseUtil.responseWrite(
                    JSON.toJSONString(ReturnInfo.getInstance().setResult(ErrorCodeEnum.AUTH_NO)),
                    servletResponse);
        }
        // 过滤链终止
        return false;
    }

    private boolean isJwtSubmission(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        String appId =  request.getHeader("AppId");
        return (request instanceof HttpServletRequest)
                && !StringUtils.isEmpty(jwt)
                && !StringUtils.isEmpty(appId);
    }

    private AuthenticationToken createJwtToken(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String appId = request.getHeader("AppId");
        String jwt = request.getHeader("Authorization");
        String ipHost = request.getRemoteAddr();
        String deviceInfo = request.getParameter("deviceInfo");
        return new JwtToken(ipHost,deviceInfo,jwt,appId);
    }

    // 验证当前用户是否属于mappedValue任意一个角色
    private boolean checkRoles(Subject subject, Object mappedValue){
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }

}
