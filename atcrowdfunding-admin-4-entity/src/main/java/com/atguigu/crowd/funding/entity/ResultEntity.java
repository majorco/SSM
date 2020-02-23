package com.atguigu.crowd.funding.entity;

/**
 * @program: atcrowdfundingadmin0parent
 * @description
 * @author: li
 * @create: 2020-02-22 20:33
 * 统一整个项目中所有的响应格式
 * 返回给前端信息的工具类
 **/
public class ResultEntity<T> {
    public static final String SUCCESS="SUCCESS";
    public static final String FAILED="FAILED"; ///失败 failed
    public static final String NO_MESSAGE="NO_MESSAGE"; //没有消息
    public static final String NO_DATA="NO_DATA";//没有数据
    //方便返回 成功结果 （不携带查询结果
    public static ResultEntity<String> successWithoutData(){
        return new ResultEntity<String>(SUCCESS,NO_MESSAGE,NO_DATA);
    }
    // 携带查询结果
    public static <E> ResultEntity<E> successAndData(E data){

        return new ResultEntity<E>(SUCCESS,NO_MESSAGE,data);
    }
//    失败 结果
    public static <E> ResultEntity<E> failed(E data,String message){
        return new ResultEntity<E>(FAILED,message,data);
    }

    private String result;
    private String message;
    //查询返回的数据
    private T data;

    public ResultEntity(){

    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
