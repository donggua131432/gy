package com.gy.mapper.sys;

import com.gy.domain.dto.sys.Dict;
import com.gy.domain.dto.sys.DictInfo;
import com.gy.domain.vo.req.DictQuery;

import java.util.List;

public interface DictMapper {

    List<Dict> selectListDictPage(DictQuery dictQuery);

    int deleteByPrimaryKey(String dicId);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(String dicId);

    List<DictInfo> selectListDictInfo();

}