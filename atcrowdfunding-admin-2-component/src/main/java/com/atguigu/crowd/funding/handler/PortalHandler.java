package com.atguigu.crowd.funding.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 15:17
 **/

@Controller
public class PortalHandler {
    @RequestMapping("/index")
    public String showIndex(){

        return "index-page";
    }
}
