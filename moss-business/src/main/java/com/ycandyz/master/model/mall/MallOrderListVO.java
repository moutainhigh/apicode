package com.ycandyz.master.model.mall;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class MallOrderListVO {

    @ApiModelProperty(value = "10-待支付  20-待发货 30-待收货 40-已收货  50-已取消 ")
    private java.lang.Integer status;
    private String userPhone;
    private String goodsNo;
    private String orderNo;
    private String item_name;
    private BigDecimal total_money;
    private String quantity;
    private String userName;
    private Integer orderAt;
    private Integer pay_type;
    private Long sellerUserId;
    private String seller_user_phone;

}
