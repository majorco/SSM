package com.atguigu.crowd.funding.securityConfig;

import com.atguigu.crowd.funding.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-10-16 21:31
 **/
public class SecurityAdmin extends User {

    private Admin originalAdmin;
    public SecurityAdmin(Admin originalAdmin, Collection<? extends GrantedAuthority> authorities) {


        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

        this.originalAdmin=originalAdmin;

        this.originalAdmin.setUserPswd(null);

    }

    public Admin getOriginalAdmin() {
        return this.originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }
}
