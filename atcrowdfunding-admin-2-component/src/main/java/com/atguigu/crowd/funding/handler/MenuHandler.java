package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-03-07 21:06
 **/
@RestController
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/admin/menu/getAll")
    public ResultEntity getAll(){
       List<Menu> allList= menuService.getAll();
        Map<Integer,Menu> menuMap=new HashMap<>();
       for(Menu menu : allList){
           menuMap.put(menu.getId(), menu);
       }

       Menu rootNode =null;
       for (Menu menu : allList){
           Integer parentId=menu.getPid();
           if (parentId == null){
               rootNode = menu;
               //执行下次循环
               continue;
           }
           //父节点不为空，
           menuMap.get(parentId).getChildrenList().add(menu);

       }

       return ResultEntity.successAndData(rootNode);

    }

}
