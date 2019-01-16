package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.OrganDefine;
import com.gy.domain.vo.res.OrganDefineVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrganDefineMapper {

    int deleteOrganDefineById(String organDefineId);

    int insertOrganDefine(OrganDefine record);

    List<OrganDefine> selectOrganDefine();

    List<OrganDefineVo> selectListOrganDefinePage(Map<String, String> paramMap);

    int updateOrganDefineById(OrganDefine record);


}