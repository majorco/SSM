package com.atguigu.crowd.funding.exception;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 22:02
 **/
@ControllerAdvice
public class CrowdFundingExceptionResolver {
    @ExceptionHandler(Exception.class)
    public ModelAndView catchException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //异常发生后，对当前请求进行检查，如果是异步请求，返回 true
        boolean checkAsyncRequested = CrowdFundingUtils.checkAsyncRequested(request);
        if (checkAsyncRequested){
            //获取异常名字
            String name = exception.getClass().getName();
            //从已经定义的异常 Map 中获取异常消息
            String exceptionMessage = CrowdFundingConstant.EXCEPTION_MESSAGE_MAP.get(name);
            if (exceptionMessage == null){
                exceptionMessage = "未知错误";
            }
            //创建 ResultEntity 进行返回
            ResultEntity<String> failed = ResultEntity.failed(ResultEntity.NO_DATA, exceptionMessage);
            //使用 Gson 将 ResultEntity 转换为 JSON
            Gson gson = new Gson();
            String s = gson.toJson(failed);
            //将JSON返回给游览器
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return null;
        }

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("exception",exception);
        modelAndView.setViewName("system-error");
        return modelAndView;
    }

}
