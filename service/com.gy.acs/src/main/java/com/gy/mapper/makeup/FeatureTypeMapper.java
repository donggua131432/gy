package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.FeatureType;
import com.gy.domain.vo.req.makeup.FeatureTypeQuery;

import java.util.List;

public interface FeatureTypeMapper {
    int deleteFeatureTypeById(String featureTypeId);

    int insertFeatureType(FeatureType featureType);

    List<FeatureType> selectListFeatureTypePage(FeatureTypeQuery featureTypeQuery);

    int updateFeatureTypeById(FeatureType record);

}