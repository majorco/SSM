package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-25 22:09
 **/
public interface RoleService {
    PageInfo<Role> selectByKeyword (String keyword,Integer pageSize,Integer pageNum);

    List<Role> selectByIdList(List<Integer> roleIdArray);

    void deleteByIdArray(List<Integer> idArray);

    boolean insertRoleByName(String name);

    boolean updateRoleByID(Role role);

    List<Role> getAssignByAdminId(Integer adminId);

    List<Role> getUnAssignByAdminId(Integer adminId);


    void updateAssignByAdminId(Integer adminId, List<Integer> roles);
}
