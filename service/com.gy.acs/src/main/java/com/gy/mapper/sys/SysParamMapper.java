package com.gy.mapper.sys;

import com.gy.domain.dto.sys.SysParam;

public interface SysParamMapper {

    int deleteSysParam(String paramId);

    int insertSysParam(SysParam record);

    SysParam selectSysParam(String paramId);

    int updateSysParam(SysParam record);
}