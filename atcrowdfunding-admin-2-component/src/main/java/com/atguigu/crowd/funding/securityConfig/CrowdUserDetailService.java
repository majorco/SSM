package com.atguigu.crowd.funding.securityConfig;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-10-16 21:40
 **/
@Component
public class CrowdUserDetailService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据账号名称查询Admin
        Admin admin = adminService.getAdminByLoginAccount(username);
        Integer id=admin.getId();
        //2.根据AdminID ，查询角色信息
        List<Role> assignedRoleList = roleService.getAssignByAdminId(id);
        //3.根据adminId查询权限信息
        List<String> assignedAuthList = authService.getAssignedAuthNameByAdminId(id);
        //4.创建集合存储Granted
        List<GrantedAuthority> authorities =new ArrayList<>();
        //5.遍历assignedList ，存入角色信息
        for(Role role:assignedRoleList){
            //角色前缀
            String roleName="ROLE_"+role.getName();
            //根据名字构造Granted
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            //存入GrantedAuthorityList
            authorities.add(simpleGrantedAuthority);
        }
        //6.遍历assignedAuthLIst ，存入权限信息
        for (String authName:assignedAuthList){
            //构造权限信息
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            //存入GrantedAuthorityList
            authorities.add(simpleGrantedAuthority);
        }
        //7.封装securityAdmin
        SecurityAdmin securityAdmin=new SecurityAdmin(admin, authorities);
        return securityAdmin;
    }
}
