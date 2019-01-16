package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.sys.Kol;
import com.gy.domain.vo.req.KolQuery;
import com.gy.domain.vo.req.PageCondition;
import com.gy.mapper.makeup.KolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: Kol服务层
 * @作者: FangLin
 * @创建时间: 2018/12/19 16:49
 * @版本号: V1.0
 *  
 */
@Service
public class KolService {
    @Autowired
    private KolMapper kolMapper;

    public PageInfo<Kol> listKolPage(KolQuery kolQuery) {
        PageHelper.startPage(kolQuery.getPage(),kolQuery.getSize());
        List<Kol> kolList = kolMapper.selectKolPage(kolQuery);
        return new PageInfo<>(kolList);
    }

    public int addKol(String kolId,String kolName) {
        return kolMapper.insertKol(kolId,kolName);
    }

    public int updateKol(Kol kol) {
        return kolMapper.updateKolById(kol);
    }

    public int deleteKol(String kolId) {
        return kolMapper.deleteKolById(kolId);
    }
}
