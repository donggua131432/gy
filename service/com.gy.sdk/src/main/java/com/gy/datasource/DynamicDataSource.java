package com.gy.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @描述: 动态链接数据源
 * @作者: DuKai
 * @创建时间: 2018/12/4 10:29
 * @版本号: V1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDB();
    }
}
