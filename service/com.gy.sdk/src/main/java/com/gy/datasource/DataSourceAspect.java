package com.gy.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @描述: 数据源拦截
 * @作者: DuKai
 * @创建时间: 2018/12/4 11:18
 * @版本号: V1.0
 */
@Aspect
@Component
public class DataSourceAspect {
    public static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Value("${jdbc.global.defaultDatasource}")
    private String defaultDatasource;


    @Before("@annotation(DB)")
    public void beforeSwitchDB(JoinPoint point){
        //获得当前访问的class
        Class<?> clazz = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = defaultDatasource;
        try {
            // 得到访问的方法对象
            Method method = clazz.getMethod(methodName, argClass);
            // 判断是否存在@DB注解
            if (method.isAnnotationPresent(DB.class)) {
                DB annotation = method.getAnnotation(DB.class);
                // 取出注解中的数据源名
                dataSource = annotation.db().get();
            }
        } catch (Exception e) {
            logger.error("数据源切换异常：{}", e);
        }
        // 切换数据源
        DBContextHolder.setDB(dataSource);
    }

    @After("@annotation(DB)")
    public void afterSwitchDB(){
        DBContextHolder.clearDB();
    }
}
