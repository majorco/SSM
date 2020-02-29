package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.entity.RoleExample;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-25 22:09
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> selectByKeyword(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectByKeyword(keyword);
        return new PageInfo<>(roles);

    }

    /**
     * 根据 IdList 查询 返回 RoleList， SQL 语句为 in(id..id,) 只要在 in 里面就会返回
     * @param roleIdArray
     * @return
     */
    @Override
    public List<Role> selectByIdList(List<Integer> roleIdArray) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleIdArray);
        //返回的是 list
        return roleMapper.selectByExample(roleExample);
    }

    /**
     * 根据 RoleID 数组 删除对应记录
     * @param idArray
     */
    @Override
    public void deleteByIdArray(List<Integer> idArray) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(idArray);
        roleMapper.deleteByExample(roleExample);
    }
}
