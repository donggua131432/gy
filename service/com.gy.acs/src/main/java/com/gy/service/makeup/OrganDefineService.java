package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.makeup.OrganDefine;
import com.gy.domain.vo.req.PageCondition;
import com.gy.domain.vo.res.OrganDefineVo;
import com.gy.mapper.makeup.OrganDefineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @描述: 重新定义器服务层
 * @作者: FangLin
 * @创建时间: 2018/12/15 14:23
 * @版本号: V1.0
 *  
 */
@Service
public class OrganDefineService {
    @Autowired
    private OrganDefineMapper organDefineMapper;

    public int addOrganDefine(OrganDefine organDefine) {
        return organDefineMapper.insertOrganDefine(organDefine);
    }

    public int updateOrganDefine(OrganDefine organDefine) {
        return organDefineMapper.updateOrganDefineById(organDefine);
    }

    public int deleteOrganDefine(String organDefineId) {
        return organDefineMapper.deleteOrganDefineById(organDefineId);
    }

    public PageInfo<OrganDefineVo> listOrganDefinePage(PageCondition pageCondition, Map<String,String> paramMap) {
        PageHelper.startPage(pageCondition.getPage(),pageCondition.getSize());
        List<OrganDefineVo> organDefineArrayList = organDefineMapper.selectListOrganDefinePage(paramMap);
        return new PageInfo<>(organDefineArrayList);
    }



}
