package com.gy.mapper.sys;

import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.vo.req.SysFileInfoQuery;

import java.util.List;

public interface SysFileInfoMapper {
    int deleteSysFileInfoById(String fileId);

    int insertSysFileInfo(SysFileInfo record);

    List<SysFileInfo> selectSysFileInfoPage(SysFileInfoQuery sysFileInfoQuery);

    int updateSysFileInfoById(SysFileInfo record);

}