package com.ycandyz.master.domain.response.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * @Description 发卷宝-优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
public class CouponActivityTicketResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠卷剩余数量")
    private Integer remainNum;

    @ApiModelProperty(value = "券生效开始时间")
    private Long beginAt;

    @ApiModelProperty(value = "券生效结束时间")
    private Long endAt;


}
