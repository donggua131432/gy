package com.gy.service.makeup;

import com.gy.domain.dto.makeup.Organ;
import com.gy.mapper.makeup.OrganMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 器官服务层
 * @作者: FangLin
 * @创建时间: 2018/12/13 15:50
 * @版本号: V1.0
 *  
 */
@Service
public class OrganService {
    @Autowired
    private OrganMapper organMapper;

    public List<Organ> listOrgan() {
        return organMapper.selectListOrgan();
    }

    public int addOrgan(Organ organ) {
        return organMapper.insertOrgan(organ);
    }

    public int updateOrgan(Organ organ) {
        return organMapper.updateOrganById(organ);
    }

    public int deleteOrgan(String organId) {
        return organMapper.deleteOrganById(organId);
    }
}
