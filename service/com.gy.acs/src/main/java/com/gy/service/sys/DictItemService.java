package com.gy.service.sys;

import com.gy.domain.dto.sys.DictItem;
import com.gy.mapper.sys.DictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 数据字典值服务层
 * @作者: FangLin
 * @创建时间: 2018/11/20 15:41
 * @版本号: V1.0
 *  
 */
@Service
public class DictItemService {
    @Autowired
    private DictItemMapper dictItemMapper;

    public List<DictItem> getDictItem(String dictId){
        return dictItemMapper.selectByPrimaryKey(dictId);
    }

    public int addDictItem( DictItem dictItem) {
        return dictItemMapper.insertSelective(dictItem);
    }

    public int updateDictItem(DictItem dictItem) {
        return dictItemMapper.updateByPrimaryKeySelective(dictItem);
    }

    public int deleteDictItem(String itemId) {
        return dictItemMapper.deleteDictItemByItemId(itemId);
    }

    public int deleteDictItemByDictId(String dictId) {
        return dictItemMapper.deleteDictItemByDictId(dictId);
    }
}
