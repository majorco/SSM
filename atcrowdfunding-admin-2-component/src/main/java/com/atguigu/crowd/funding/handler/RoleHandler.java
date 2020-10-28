package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-25 22:10
 **/
@RestController
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('部长')")
    @RequestMapping(value = "/admin/query/forRole/search")
    public ResultEntity<PageInfo<Role>> queryForKeyword(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ) {
        PageInfo<Role> rolePageInfo = roleService.selectByKeyword(keyword, pageSize, pageNum);

        return ResultEntity.successAndData(rolePageInfo);

    }

    @RequestMapping("/role/getList/byIdArray/list")
    public ResultEntity<List<Role>> queryRoleListById(@RequestBody List<Integer> roleIdArray) {
            List<Role> roleList = roleService.selectByIdList(roleIdArray);
            return ResultEntity.successAndData(roleList);
    }


    /**
     * 根据 RoleID 数组 删除对应记录
     * @param idArray
     * @return
     */
    @RequestMapping("do/deleteRole/byIdArray")
    public ResultEntity batchRemove(@RequestBody List<Integer> idArray){
            roleService.deleteByIdArray(idArray);
            return ResultEntity.successWithoutData();
    }

    /**
     * 新增
     * @param name
     * @return
     */
    @RequestMapping("do/save/role")
    public ResultEntity insertRole(@RequestParam("name") String name){
          roleService.insertRoleByName(name);
          return ResultEntity.successWithoutData();
    }

    @RequestMapping("/do/updateRole/byID")
    public ResultEntity<String> updateRoleById(Role role){
            roleService.updateRoleByID(role);
            return ResultEntity.successWithoutData();
    }

}
