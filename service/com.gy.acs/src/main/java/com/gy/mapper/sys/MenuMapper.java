package com.gy.mapper.sys;

import com.gy.domain.dto.sys.Menu;
import com.gy.domain.vo.req.MenuQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    int deleteMenuById(String menuId);

    int insertMenu(Menu menu);

    Menu selectMenuById(String menuId);

    List<Menu> selectListAllMenu();

    List<Menu> selectListMenuPage(MenuQuery  menuQuery);

    int updateMenu(Menu menu);

    List<String> selectListMenuParentId(String menuId);
}