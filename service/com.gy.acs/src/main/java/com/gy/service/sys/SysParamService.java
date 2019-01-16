package com.gy.service.sys;

import com.gy.datasource.DB;
import com.gy.datasource.Database;
import com.gy.domain.dto.sys.SysParam;
import com.gy.mapper.sys.SysParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 获取系统参数服务层
 * @作者: DuKai
 * @创建时间: 2018/12/3 17:59
 * @版本号: V1.0
 */

@Service
public class SysParamService {

    @Autowired
    private SysParamMapper sysParamMapper;


    /**
     * 获取系统参数
     * @param paramId
     * @return
     */
    @DB(db = Database.LC)
    public SysParam getSysParam(String paramId){
        return sysParamMapper.selectSysParam(paramId);
    }

}
