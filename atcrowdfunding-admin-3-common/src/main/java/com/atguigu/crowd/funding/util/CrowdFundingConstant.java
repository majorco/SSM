package com.atguigu.crowd.funding.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 21:39
 **/
//常量
public class CrowdFundingConstant {
    public static final String ATTR_NAME_MESSAGE="MESSAGE";
    public static final String ATTR_NAME_LOGIN_ADMIN="LOGIN-ADMIN";
    public static final String MESSAGE_LOGIN_FAILED="登录的账号或密码不正确,请核对后再登录";
    public static final String MESSAGE_CODE_INVALID="空的密码！";
    public static final String  MESSAGE_ACCESS_DENIED="请登录后再操作";
    public static final String ATTR_NAME_PAGE_INFO ="PAGE-INFO";
    public static final String LOGIN_ACCT_ALREADY_IN_USE ="登录账户已被使用，请重新设定";
    public static final Map<String,String> EXCEPTION_MESSAGE_MAP = new HashMap<>();
    static {
        EXCEPTION_MESSAGE_MAP.put("org.springframework.dao.DuplicateKeyException", "名称已经存在");

    }
}
