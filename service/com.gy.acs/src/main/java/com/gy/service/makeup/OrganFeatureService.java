package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.makeup.OrganFeature;
import com.gy.domain.vo.req.makeup.OrganFeatureQuery;
import com.gy.domain.vo.res.OrganFeatureVo;
import com.gy.mapper.makeup.OrganFeatureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 器官特征值服务层
 * @作者: FangLin
 * @创建时间: 2018/12/13 16:01
 * @版本号: V1.0
 *  
 */
@Service
public class OrganFeatureService {
    @Autowired
    private OrganFeatureMapper organFeatureMapper;

    public PageInfo<OrganFeatureVo> listOrganFeaturePage(OrganFeatureQuery organFeatureQuery) {
        PageHelper.startPage(organFeatureQuery.getPage(),organFeatureQuery.getSize());
        List<OrganFeatureVo> organComprehensiveList = organFeatureMapper.selectListOrganFeaturePage(organFeatureQuery);
        return new PageInfo<>(organComprehensiveList);
    }

    public int addOrganFeature(OrganFeature organFeature) {
        return organFeatureMapper.insertOrganFeature(organFeature);
    }

    public int updateOrganFeature(OrganFeature organFeature) {
        return organFeatureMapper.updateOrganFeatureById(organFeature);
    }

    public int deleteOrganFeature(String organFeatureId) {
        return organFeatureMapper.deleteOrganFeatureById(organFeatureId);
    }
}
