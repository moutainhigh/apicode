package com.ycandyz.master.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.entities.coupon.Coupon;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 优惠卷 实体类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon")
public class CouponDTO extends Coupon {

    private Long id;

    @ApiModelProperty(value = "门店编号")
    private String shopNo;

    @ApiModelProperty(value = "优惠券编码")
    private String couponNo;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "券总数量")
    private Integer ticketSum;

    @ApiModelProperty(value = "领取数量")
    private Integer couponSum;

    @ApiModelProperty(value = "核销数量")
    private Integer useNum;

    @ApiModelProperty(value = "优惠券状态：0:停止；1:启用")
    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
