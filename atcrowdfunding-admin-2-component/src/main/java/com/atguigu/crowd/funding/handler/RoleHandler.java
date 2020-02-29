package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-25 22:10
 **/
@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/admin/query/forRole/search")
    public ResultEntity<PageInfo<Role>> queryForKeyword(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ) {
        PageInfo<Role> rolePageInfo = roleService.selectByKeyword(keyword, pageSize, pageNum);

        return ResultEntity.successAndData(rolePageInfo);

    }

    @ResponseBody
    @RequestMapping("/role/getList/byIdArray/list")
    public ResultEntity<List<Role>> queryRoleListById(@RequestBody List<Integer> roleIdArray) {
        List<Role> roleList = null;
        try {
            roleList = roleService.selectByIdList(roleIdArray);
        } catch (Exception e) {
            return ResultEntity.failed(null, e.getMessage());
        }
            return ResultEntity.successAndData(roleList);
    }


    /**
     * 根据 RoleID 数组 删除对应记录
     * @param idArray
     * @return
     */
    @ResponseBody
    @RequestMapping("do/deleteRole/byIdArray")
    public ResultEntity batchRemove(@RequestBody List<Integer> idArray){
        try {
            roleService.deleteByIdArray(idArray);
        }catch (Exception e){
            return ResultEntity.failed(null,e.getMessage());
        }
        return ResultEntity.successWithoutData();
    }
}
