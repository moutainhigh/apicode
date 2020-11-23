package com.ycandyz.master.domain.query.coupon;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 发卷宝-优惠卷 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="发卷宝-优惠卷-检索参数")
public class CouponActivityTicketQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiParam(hidden = true)
    @ApiModelProperty(name = "shop_no",value = "门店编号")
    private String shopNo;

    @ApiModelProperty(name = "activity_no",value = "优惠宝编号")
    private String activityNo;

    @ApiModelProperty(value = "优惠卷名称")
    private String name;

}
