package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Auth;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-06-29 22:08
 **/
public interface AuthService {
    List<Auth> getAllAuth();
}
