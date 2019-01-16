package com.gy.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.vo.req.MenuQuery;
import com.gy.mapper.sys.MenuMapper;
import com.gy.domain.dto.sys.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 菜单服务层
 * @作者: FangLin
 * @创建时间: 2018/11/16 17:58
 * @版本号: V1.0
 *  
 */
@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public int addMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    public List<Menu> listAllMenu(){
        return menuMapper.selectListAllMenu();
    }

    public PageInfo<Menu> listMenuPage(MenuQuery menuQuery){
        PageHelper.startPage(menuQuery.getPage(), menuQuery.getSize());
        List<Menu> menuList = menuMapper.selectListMenuPage(menuQuery);
        return new PageInfo<>(menuList);
    }

    public int updateMenu(Menu menu){
        return menuMapper.updateMenu(menu);
    }

    public int deleteMenu(String menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    public List<String> listMenuParentId(String menuId) {
        return menuMapper.selectListMenuParentId(menuId);
    }

}
