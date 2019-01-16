package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.OrganDefineArray;
import com.gy.domain.vo.res.OrganDefineArrayVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrganDefineArrayMapper {

    int deleteOrganDefineArrayBatch(@Param("arrStrList") List<String> arrStrList);

    int deleteOrganDefineArray(@Param("organDefineId") String organDefineId);

    int insertOrganDefineArrayBatch(@Param("organDefineArrayList") List<OrganDefineArray> organDefineArrayList);

    List<OrganDefineArrayVo> selectOrganDefineArrayVo(@Param("organDefineId") String organDefineId);

    List<String> selectOrganDefineArrayById(@Param("organDefineId") String organDefineId);

}