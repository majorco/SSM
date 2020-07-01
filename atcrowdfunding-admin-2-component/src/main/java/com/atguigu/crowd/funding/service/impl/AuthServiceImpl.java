package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.AuthExample;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<Integer> getAssignIdList(Integer roleId) {
        return authMapper.selectAssignIdList(roleId);
    }

    @Override
    public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDateMap) {
        List<Integer> roleIdList = assignDateMap.get("roleIdList");
        List<Integer> authList = assignDateMap.get("authList");

        Integer roleId = roleIdList.get(0);
        //删除旧数据
        authMapper.deleteOldRelationShip(roleId);

        //保存新数据
        if (CrowdFundingUtils.collectionEffective(authList)){
            authMapper.insertNewRelationShip(roleId,authList);
        }

    }
}
