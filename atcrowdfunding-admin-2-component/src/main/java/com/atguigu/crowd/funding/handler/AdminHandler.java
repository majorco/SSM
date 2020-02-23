package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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

//    @RequestBody  获取请求体数据
//    @ResponseBody 将数据转成Json数据发送给Brower

    @ResponseBody
    @RequestMapping(value = "/admin/batch/remove")
    public ResultEntity<String> batchRemove(@RequestBody List<Integer> adminIdArray){
        try {
            adminService.batchRemove(adminIdArray);

            return ResultEntity.successWithoutData();
        }catch (Exception e){

            return ResultEntity.failed(null, e.getMessage());
        }
    }

    /**
     * 分页 要配置 plugin
     * @param pageNum 页码 默认为1
     * @param pageSize 每页显示的数量 默认为1
     * @param keyword 关键字 默认为空 查所有
     * @param model 往 request 域存入数据
     * @return admin-page 页面
     */
    @RequestMapping("/admin/query/for/search")
    public String queryForSearch(
            //如果 页面没有提供 请求参数 ，使用 defaultValue
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            Model model){
        PageInfo<Admin> adminPageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
        model.addAttribute(CrowdFundingConstant.ATTR_NAME_PAGE_INFO, adminPageInfo);
        return "admin-page";
    }
    //退出登录
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
        // session 域 放入 Admin 对象
        httpSession.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        //登录成功去的页面
        return "redirect:/admin/to/main/page.html";
    }

}















