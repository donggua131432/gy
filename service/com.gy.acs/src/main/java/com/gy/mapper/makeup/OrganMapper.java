package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.Organ;

import java.util.List;

public interface OrganMapper {
    int deleteOrganById(String organId);

    int insertOrgan(Organ record);

    List<Organ> selectListOrgan();

    int updateOrganById(Organ record);

}