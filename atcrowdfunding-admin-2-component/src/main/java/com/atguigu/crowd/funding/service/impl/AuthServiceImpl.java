package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.AuthExample;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-06-29 22:08
 **/
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<Auth> getAllAuth() {
       return authMapper.selectByExample(new AuthExample());

    }
}
