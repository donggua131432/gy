package com.gy.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @描述: 处理数据源
 * @作者: DuKai
 * @创建时间: 2018/12/4 10:31
 * @版本号: V1.0
 */
public class DBContextHolder {
    public static final Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源名
     * @param dbType
     */
    public static void setDB(String dbType){
        logger.info("切换到数据源: {}",dbType);
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源名
     * @return
     */
    public static String getDB(){
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDB(){
        contextHolder.remove();
    }
}
