package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @RequestMapping("/admin/do/update")
    public String doUpdate(Admin admin,
                           @RequestParam("pageNum") String pageNum,
                           @RequestParam("keyword") String keyword){
        try {
            adminService.updateAdmin(admin);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                throw new RuntimeException(CrowdFundingConstant.LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        return "redirect:/admin/query/for/search.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    /**
     * 由 admin-page 点击 小笔跳转到 更新页面
     * @param id 主键
     * @param model 存入 admin
     * @return
     */
    @RequestMapping("/admin/to/update")
    public String toUpdate(@RequestParam("adminId") Integer id,Model model){
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin",admin);
        return "admin-update";
    }

    /**
     * 新增操作
     * @param admin
     * @return 重定向到 分页 页面，避免  url 不变 导致重新提交表单
     *
     */
    @RequestMapping(value = "admin/do/add.html")
    //mvc 根据表单名字 对应的属性 封装
    public String add(Admin admin){
        try {
            adminService.add(admin);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                throw new RuntimeException(CrowdFundingConstant.LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        //设置最大值，新增后跳转 新增页
        return "redirect:/admin/query/for/search.html";

    }

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















