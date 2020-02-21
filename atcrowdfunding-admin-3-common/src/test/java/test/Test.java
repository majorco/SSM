package test;

import com.atguigu.crowd.funding.util.CrowdFundingUtils;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-19 18:12
 **/
public class Test {
    @org.junit.Test
    public void testMD5ATString(){
        String source="123123";
        String s = CrowdFundingUtils.md5(source);
        System.out.println(s);
    }
}
