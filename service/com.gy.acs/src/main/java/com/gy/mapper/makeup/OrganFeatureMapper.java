package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.OrganFeature;
import com.gy.domain.vo.req.makeup.OrganFeatureQuery;
import com.gy.domain.vo.res.OrganFeatureVo;

import java.util.List;

public interface OrganFeatureMapper {

    int deleteOrganFeatureById(String featureId);

    int insertOrganFeature(OrganFeature organFeature);

    List<OrganFeatureVo> selectListOrganFeaturePage(OrganFeatureQuery organFeatureQuery);

    int updateOrganFeatureById(OrganFeature organFeature);

}