package com.atguigu.crowd.funding.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 22:02
 **/
@ControllerAdvice
public class CrowdFundingExceptionResolver {
    @ExceptionHandler(Exception.class)
    public ModelAndView catchException(Exception exception){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("exception",exception);

            modelAndView.setViewName("system-error");
        return modelAndView;
    }

}
