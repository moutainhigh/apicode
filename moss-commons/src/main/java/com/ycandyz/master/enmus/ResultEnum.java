package com.ycandyz.master.enmus;

import lombok.Getter;

/**
 * @author Wang Yang
 */

@Getter
public enum ResultEnum {

    /**
     * 登陆注册返回code
     */
    LOGIN_SUCCESS(10000, "登录成功"),
    LOGIN_FAIL(10001, "登录失败"),
    REGISTER_FAIL(10002, "注册失败"),
    USER_PWD_ERROR(10003, "用户名或密码错误"),
    USER_EXIST(10004, "用户已存在"),
    USER_NOT_EXIST(10005, "用户不存在"),
    PASSWORD_ERROR(10006, "密码错误"),
    USER_IS_CANCEL(10007, "该用户已注销，请联系平台管理员恢复"),

    /**
     * 返回token认证code
     */
    TOKEN_IS_NULL(10008, "token为空"),
    TOKEN_INVALID(10009, "token无效"),
    TOKEN_ILLEGAL(10010, "token不合法");

    private int value;

    private String desc;

    public int value() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String desc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ResultEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
