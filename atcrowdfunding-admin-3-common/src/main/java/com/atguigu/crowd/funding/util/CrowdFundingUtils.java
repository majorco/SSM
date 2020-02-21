package com.atguigu.crowd.funding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

/**
 * @program: 工具类
 * @description
 * @author: li
 * @create: 2020-02-19 17:17
 **/
public class CrowdFundingUtils {
    /**
     * 检测 map 是否可用
     * @param map
     * @param <K>
     * @param <V>
     * @return true 可用  false 不可用
     */
    public static <K, V> boolean mapEffective(Map<K,V> map){
        return map!=null && map.size()>0;
    }

    /**
     * 判断集合是否有效
     * List Set 是Collection的子接口 map 不是 没有关系
     * @param collection
     * @param <E>
     * @return ture 有效 false 无效
     */

    public static <E> boolean collectionEffective(Collection<E> collection){
        return collection!=null && collection.size()>0;
    }
    /**
     * 字符串是否有效
     * @param source
     * @return ture 有效 false 无效
     */
    public static boolean stringEffective(String source){
        return source!=null && source.length()>0;
    }

    /**
     * 加密方法
     * @param source 明文
     * @return 密文
     *
     */
    public static String md5(String source){
        //判断 是否有效
        if(!stringEffective(source)){
            throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
        }
        char[] chars={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String algorithm="MD5";
        StringBuilder builder = new StringBuilder();
        try {
            //java提供的，用MessageDigest 执行加密
            //algorithm 算法
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            //将要加密的明文转换为字节数组
            byte[]  inputBytes= source.getBytes();
                //执行加密
            byte[] outputBytes = instance.digest(inputBytes);
            for (int i=0;i<outputBytes.length;i++){
                //获取当前字节
                byte b = outputBytes[i];
                //获取低4位 一个字节等于8位，跟0000 1111 （15） 与运算 得到低四位
                int lowValue=b & 15;
                //右移 四位得到 在与 15 运算 得到高四位
                int highValue=(b>>4) & 15;
                //以 高低 四位的十进制 从 数组中获取
                char highC=chars[highValue];
                char lowC=chars[lowValue];
                builder.append(highC).append(lowC);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
