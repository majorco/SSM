package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-03-15 23:46
 **/
@Controller
public class AssignHandler {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/get/all/auth")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList=authService.getAllAuth();
        return ResultEntity.successAndData(authList);
    }
    @RequestMapping("/admin/to/assign/page")
    public String selectAssign(@RequestParam("adminId") Integer adminId, Model model){
       List<Role> assign=roleService.getAssignByAdminId(adminId);
        List<Role> unAssign=roleService.getUnAssignByAdminId(adminId);
        model.addAttribute("assignedRoleList",assign);
        model.addAttribute("unAssignedRoleList",unAssign);
        return "assign-page";
    }

    @RequestMapping("/assign/role")
    public String updateDoAssign(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") String pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList",required = false) List<Integer> roles){
            roleService.updateAssignByAdminId(adminId,roles);
        return "redirect:/admin/query/for/search.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
