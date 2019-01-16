package com.gy.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.sys.Dict;
import com.gy.domain.dto.sys.DictInfo;
import com.gy.domain.vo.req.DictQuery;
import com.gy.mapper.sys.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 数据字典服务层
 * @作者: FangLin
 * @创建时间: 2018/11/20 13:54
 * @版本号: V1.0
 *  
 */
@Service
public class DictService {
    @Autowired
    private DictMapper dictMapper;

    public PageInfo<Dict> listDictPage(DictQuery dictQuery) {
        PageHelper.startPage(dictQuery.getPage(),dictQuery.getSize());
        List<Dict> dictList = dictMapper.selectListDictPage(dictQuery);
        return new PageInfo<>(dictList);
    }

    public int addDict(Dict dict) {
        return dictMapper.insertSelective(dict);
    }

    public int deleteDict(String dictId) {
        return dictMapper.deleteByPrimaryKey(dictId);
    }

    public List<DictInfo> listDictInfo(){
        return dictMapper.selectListDictInfo();
    }
}
