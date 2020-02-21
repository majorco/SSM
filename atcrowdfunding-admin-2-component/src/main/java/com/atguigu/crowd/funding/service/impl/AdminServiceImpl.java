package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-18 20:27
 **/
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAll() {
        AdminExample example=new AdminExample();
        return adminMapper.selectByExample(example);
    }

    @Override
    public void updateAdmin() {
        AdminExample example=new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo("poter");
        adminMapper.updateByExample(new Admin("jeck", "jeck", "杰克", "jeck@qq", null), example);
        adminMapper.updateByPrimaryKey(new Admin(1, "harry", "哈利", "harry", "harry@qq", null));
    }

    @Override
    public Admin login(String loginAcct, String userPswd) {
        AdminExample example=new AdminExample();
        example.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> list = adminMapper.selectByExample(example);
//        查询
        if (!CrowdFundingUtils.collectionEffective(list)){
            //如果为 null
           return null;
        }
        Admin admin = list.get(0);
        if (admin==null){
            return null;
        }
       //获取 查询出的账户密码
        String userPswdDataBase = admin.getUserPswd();

        String userPswdBrowser = CrowdFundingUtils.md5(userPswd.trim());
        if (Objects.equals(userPswdDataBase, userPswdBrowser)){
            //如果 密码相等 那么 登录成功
            return admin;
        }
        return null;
    }





}
