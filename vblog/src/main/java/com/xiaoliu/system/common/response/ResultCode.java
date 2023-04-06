package com.xiaoliu.system.common.response;

/**
 * @author: 61分
 * @date: 2022/7/29 14:03
 * @description:
 */
public enum ResultCode implements CustomizeResultCode{
   SUCCESS(20000,"成功"),
    ERROR(20001,"失败"),
    ERROR_TOKEN(20002,"token已失效，请重新登录"),
    LOGIN_EXCEPTION(20003,"未登录，请先登录"),
    USER_NOT_FOUND(20004,"用户不存在"),
    PWD_ERROR(20005,"密码错误"),
    BLOG_EXCEPTION(20006,"该博客不存在"),
    USER_EXISTED(20007,"用户已存在"),
    MENU_EXISTED(20008,"菜单已存在"),
    INSERT_EXCEPTION(20009,"插入异常"),
    FILE_EXCEPTION(20010,"文件过大！")


    ;
   private Integer code;
   private String msg;

   ResultCode(Integer code,String msg){
       this.code = code;
       this.msg = msg;
   }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
