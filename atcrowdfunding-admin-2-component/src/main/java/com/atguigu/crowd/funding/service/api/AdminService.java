package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-18 20:26
 **/
public interface AdminService {
    List<Admin> getAll();
    void updateAdmin(Admin admin);
    Admin login(String loginAcct, String userPswd);
    PageInfo<Admin> queryForKeywordSearch(Integer pageNum,Integer pageSize,String keyword);

    void batchRemove(List<Integer> adminIdArray);

    void add(Admin admin);

    Admin getAdminById(Integer id);
}
