package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Menu;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-03-07 21:07
 **/
public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    Menu getById(Integer menuId);

    void updateById(Menu menu);

    void deleteById(Integer id);
}
