package com.gy.service.makeup;

import com.gy.domain.dto.makeup.OrganDefineArray;
import com.gy.mapper.makeup.OrganDefineArrayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 重新定义器官排列组合服务
 * @作者: FangLin
 * @创建时间: 2018/12/15 14:22
 * @版本号: V1.0
 */

@Service
public class OrganDefineArrayService {
    @Autowired
    private OrganDefineArrayMapper organDefineArrayMapper;

    /**
     * 获取根据重新定义器官名ID获取排列组合字符串
     * @param organDefineId
     * @return
     */
    public List<String> listOrganDefineArrayById(String organDefineId) {
        return organDefineArrayMapper.selectOrganDefineArrayById(organDefineId);
    }

    /**
     * 批量添加重新定义器官名排列组合信息
     * @param organDefineArrayList
     * @return
     */
    public int addOrganDefineArrayBatch(List<OrganDefineArray> organDefineArrayList) {
        return organDefineArrayMapper.insertOrganDefineArrayBatch(organDefineArrayList);
    }

    /**
     * 批量删除重新定义器官名排列组合信息
     * @param arrStrList
     * @return
     */
    public int deleteOrganDefineArrayBatch(List<String> arrStrList) {
        return organDefineArrayMapper.deleteOrganDefineArrayBatch(arrStrList);
    }

    /**
     * 根据新定义器官名ID删除重新定义器官名排列组合信息
     * @param organDefineId
     * @return
     */
    public int deleteOrganDefineArray(String organDefineId) {
        return organDefineArrayMapper.deleteOrganDefineArray(organDefineId);
    }
}
