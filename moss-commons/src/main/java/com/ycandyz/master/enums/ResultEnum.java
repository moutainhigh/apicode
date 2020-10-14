package com.ycandyz.master.enums;

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

    /** 返回token认证code */
    TOKEN_IS_NULL(10008, "token为空"),
    TOKEN_INVALID(10009, "token无效"),
    TOKEN_ILLEGAL(10010, "token不合法"),
    /** 返回 购物车订单 code */
    MALL_PICK_ADDRESS_IS_NULL(20010, "请选择自提商家地址"),
    MALL_DELIVERY_ADDRESS_ADD(20011, "请添加收货地址"),
    MALL_DELIVERY_ADDRESS_IS_NULL(20012, "收货人收货地址为空"),
    MALL_DELIVERY_PERSON_IS_NULL(20013, "收货人姓名为空"),
    MALL_RESERVE_PHONE_IS_NULL(20014, "请填写预留电话"),
    MALL_ITEM_OFF_SHELF(20015, "商品已下架，购买失败"),
    MALL_ITEM_SOLD_OUT(20016, "商品已售罄"),
    MALL_ITEM_LOW_STOCKS(20017, "商品库存不足起售件数，无法购买"),
    MALL_ITEM_LIMIT_ORDERS(20018, "商品已超过可购买单数限制"),
    MALL_ITEM_LIMIT_SKUS(20019, "商品已超过可购买件数限制"),
    MALL_ITEM_IS_FULL(20020, "购物车已经满啦"),
    MALL_ITEM_MAX_ORDER (20021, "最多支持60个商品下单～");

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
