package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.makeup.FeatureType;
import com.gy.domain.vo.req.makeup.FeatureTypeQuery;
import com.gy.mapper.makeup.FeatureTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 特征类型服务层
 * @作者: FangLin
 * @创建时间: 2018/12/13 16:06
 * @版本号: V1.0
 *  
 */
@Service
public class FeatureTypeService {
    @Autowired
    private FeatureTypeMapper featureTypeMapper;
    public PageInfo<FeatureType> selectListFeatureTypePage(FeatureTypeQuery featureTypeQuery) {
        PageHelper.startPage(featureTypeQuery.getPage(),featureTypeQuery.getSize());
        List<FeatureType> featureTypeList = featureTypeMapper.selectListFeatureTypePage(featureTypeQuery);
        return new PageInfo<>(featureTypeList);
    }

    public int addFeatureType(FeatureType featureType) {
        return featureTypeMapper.insertFeatureType(featureType);
    }

    public int updateFeatureType(FeatureType featureType) {
        return featureTypeMapper.updateFeatureTypeById(featureType);
    }

    public int deleteFeatureType(String featureTypeId) {
        return featureTypeMapper.deleteFeatureTypeById(featureTypeId);
    }
}
