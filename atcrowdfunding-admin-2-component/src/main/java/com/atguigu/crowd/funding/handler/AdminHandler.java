package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 14:16
 **/
@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;
    //推出登录
    @RequestMapping("/admin/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/index.html";
    }
    //登录方法
    @RequestMapping("/admin/do/login")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            Model model,
            HttpSession httpSession){
        //调用 service 方法 处理逻辑 ，返回Admin
        Admin admin = adminService.login(loginAcct, userPswd);
        if (admin==null){
            //相当于往 request 域对象 存入
            model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
            return "admin-login";
        }
        httpSession.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "admin-main";
    }
}















