package com.gy.mapper.sys;

import com.gy.domain.dto.sys.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictItemMapper {

    int deleteDictItemByItemId(String itemId);

    int insertSelective(DictItem record);

    List<DictItem> selectByPrimaryKey(String dictId);

    List<Map<String, String>> selectDictItemsByDictId(@Param("dictId") String dictId);

    int updateByPrimaryKeySelective(DictItem record);

    int deleteDictItemByDictId(String dictId);
}