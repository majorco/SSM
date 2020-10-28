package com.atguigu.crowd.funding.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-10-09 13:50
 **/
//表示当前是个配置类
@Configuration
//启动web环境权限控制功能
@EnableWebSecurity
//启用全局方法过滤
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                //匹配这些
                .antMatchers("/index.html","/bootstrap/**","/css/**",
                "/fonts/**","/img/**","/jquery/**","/layer/**",
                "/script/**","/ztree/**")
                //允许所有
                .permitAll()
                .antMatchers("/admin/query/for/search.html")
                .hasRole("经理")
                //其他请求
                .anyRequest()
                //都要认证
                .authenticated();
        //前往表单登陆的界面
        security.formLogin()
                .loginPage("/admin/to/login/page.html")
                .permitAll()
                //登陆过程处理地址
                .loginProcessingUrl("/do/login.html")

                //匹配账户密码
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .and()
                //退出
                .logout()
                .logoutUrl("/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html");

    }
}
