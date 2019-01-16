package com.gy.mapper.makeup;

import com.gy.domain.dto.sys.WholeMakeup;
import com.gy.domain.vo.req.makeup.WholeMakeupQuery;
import com.gy.domain.vo.res.WholeMakeupVo;

import java.util.List;

public interface WholeMakeupMapper {
    int deleteWholeMakeupById(String wholeMakeupId);

    int insertWholeMakeup(WholeMakeup record);

    List<WholeMakeupVo> selectWholeMakeupPage(WholeMakeupQuery wholeMakeupQuery);

    int updateWholeMakeupById(WholeMakeup record);

}