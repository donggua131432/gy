package com.gy.service;

import com.gy.mapper.sys.MenuMapper;
import com.gy.model.sys.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/11/16 17:58
 *  * @版本号: V1.0
 *  
 */
@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public int addMenu(Menu menu) {
        return menuMapper.insertSelective(menu);
    }
}
