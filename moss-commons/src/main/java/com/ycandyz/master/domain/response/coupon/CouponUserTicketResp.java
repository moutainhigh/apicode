package com.ycandyz.master.domain.response.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * @Description 用户-优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponUserTicketResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(name = "use_type",value = "使用门槛：0:无门槛，1:订单满减可用")
    private Integer useType;

    @ApiModelProperty(name = "full_money",value = "订单满多少元可用")
    private BigDecimal fullMoney;

    @ApiModelProperty(name = "discount_money",value = "优惠金额")
    private BigDecimal discountMoney;

    @ApiModelProperty(name = "status", value = "使用状态(0未使用,1已使用)")
    private Integer status;

    @ApiModelProperty(name = "status_type", value = "优惠券状态分类：0:待使用,1:过期,2:已使用")
    private Integer statusType;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(name = "create_time",value = "参与时间")
    private Date createTime;


}
