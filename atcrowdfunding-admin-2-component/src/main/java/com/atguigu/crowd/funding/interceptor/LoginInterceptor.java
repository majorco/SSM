package com.atguigu.crowd.funding.interceptor;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-20 14:45
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 执行 Controller 方法之前调用
     * @param request
     * @param response
     * @param handler
     * @return Boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //从 session 获取 Admin 登录信息
        Admin admin = (Admin) session.getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
        //如果为空 说明没登陆
        if (admin==null){
            //在 request 域对象放个 错误 消息，
            request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
            //转发到 登录页面 路径：
            request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
            return false;
        }
        //如果 admin 对象不为空
        return true;
    }


}
