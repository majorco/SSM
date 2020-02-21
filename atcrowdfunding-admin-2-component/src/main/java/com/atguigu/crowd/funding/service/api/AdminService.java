package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Admin;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-18 20:26
 **/
public interface AdminService {
    public List<Admin> getAll();
    public void updateAdmin();
    Admin login(String loginAcct, String userPswd);
}