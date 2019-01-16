package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.sys.WholeMakeup;
import com.gy.domain.vo.req.makeup.WholeMakeupQuery;
import com.gy.domain.vo.res.WholeMakeupVo;
import com.gy.mapper.makeup.WholeMakeupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 整装库服务层
 * @作者: FangLin
 * @创建时间: 2018/12/21 17:03
 * @版本号: V1.0
 */
@Service
public class WholeMakeupService {
    @Autowired
    private WholeMakeupMapper wholeMakeupMapper;

    public PageInfo<WholeMakeupVo> listWholeMakeupPage(WholeMakeupQuery wholeMakeupQuery) {
        PageHelper.startPage(wholeMakeupQuery.getPage(),wholeMakeupQuery.getSize());
        List<WholeMakeupVo> wholeMakeupList = wholeMakeupMapper.selectWholeMakeupPage(wholeMakeupQuery);
        return new PageInfo<>(wholeMakeupList);
    }

    public int addWholeMakeup(WholeMakeup wholeMakeup) {
        return wholeMakeupMapper.insertWholeMakeup(wholeMakeup);
    }

    public int updateWholeMakeup(WholeMakeup wholeMakeup) {
        return wholeMakeupMapper.updateWholeMakeupById(wholeMakeup);
    }

    public int deleteWholeMakeup(String wholeMakeupId) {
        return wholeMakeupMapper.deleteWholeMakeupById(wholeMakeupId);
    }
}
