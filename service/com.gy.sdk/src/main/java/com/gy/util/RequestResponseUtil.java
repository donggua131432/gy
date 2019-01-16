package com.gy.util;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @描述: 过滤XSS SQL 数据工具类
 * @作者: DuKai
 * @创建时间: 2018/11/19 20:18
 * @版本号: V1.0
 */
public class RequestResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseUtil.class);


    /**
     * 封装response  统一json返回
     * @param outStr
     * @param servletResponse
     */
    public static void responseWrite(String outStr, ServletResponse servletResponse) {
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("access-control-allow-origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,Accept, X-Requested-With, remember-me, auth, Cookie");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(outStr);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally {
            if (null != out) {
                out.close();
            }
        }
    }


}
