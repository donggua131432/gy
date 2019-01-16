package com.gy.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.vo.req.SysFileInfoQuery;
import com.gy.mapper.sys.SysFileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @描述: ${DESCRIPTION}
 * @作者: FangLin
 * @创建时间: 2018/12/21 11:45
 * @版本号: V1.0
 */
@Service
public class SysFileInfoService {
    @Autowired
    private SysFileInfoMapper sysFileInfoMapper;

    public PageInfo<SysFileInfo> listSysFileInfoPage(SysFileInfoQuery sysFileInfoQuery) {
        PageHelper.startPage(sysFileInfoQuery.getPage(),sysFileInfoQuery.getSize());
        List<SysFileInfo> sysFileInfoList = sysFileInfoMapper.selectSysFileInfoPage(sysFileInfoQuery);
        return new PageInfo<>(sysFileInfoList);
    }

    public int addSysFileInfo(SysFileInfo sysFileInfo) {
        return sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    public int updateSysFileInfo(SysFileInfo sysFileInfo) {
        return sysFileInfoMapper.updateSysFileInfoById(sysFileInfo);
    }

    public int deleteSysFileInfo(String fileId) {
        return sysFileInfoMapper.deleteSysFileInfoById(fileId);
    }
}
