package com.ycandyz.master.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycandyz.master.entities.coupon.CouponDetail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * @Description 优惠券详情表 实体类
 * </p>
 *
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
public class CouponDetailDTO extends CouponDetail {

    private Long id;

    @ApiModelProperty(value = "优惠券详情编码")
    private String couponDetailNo;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "适用商品：0:全部，1:指定可用，2:指定不可用")
    private Integer shopType;

    @ApiModelProperty(value = "使用门槛：0:无门槛，1:订单满减可用")
    private Integer useType;

    @ApiModelProperty(value = "订单满多少元可用")
    private BigDecimal fullMoney;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountMoney;

    @ApiModelProperty(value = "券有效期类型：0:时间段(自然日)，1:领券当日起计算天数，2:领券次日起计算天数")
    private Integer status;

    @ApiModelProperty(value = "券生效开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "券生效结束时间")
    private Date endTime;

    @ApiModelProperty(value = "券有效天数")
    private Integer days;

    @ApiModelProperty(value = "领取人限制：0:所有用户")
    private Integer userType;

    @ApiModelProperty(value = "每人限领次数")
    private Integer takeNum;

    @ApiModelProperty(value = "叠加使用：0:购买限制使用一张优惠券")
    private Integer superposition;

    @ApiModelProperty(value = "领取页面：0:购物车页面直接获取，1:商品详情页直接获取")
    private Integer obtain;

    @ApiModelProperty(value = "说明备注")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //冗余字段

    @ApiModelProperty(value = "剩余数量")
    private Integer surplusNum;

    @ApiModelProperty(value = "生成时间")
    private Long ticketCreatedAt;

    @ApiModelProperty(value = "门店编号")
    private String shopNo;

    @ApiModelProperty(value = "券总数量")
    private Integer ticketSum;

    @ApiModelProperty(value = "领取数量")
    private Integer obtainNum;

    @ApiModelProperty(value = "核销数量")
    private Integer useNum;

    @ApiModelProperty(value = "优惠券状态：0:未开始，1:进行中，2:已结束，3:已停止")
    private Integer state;

    @ApiModelProperty(value = "优惠券最新修改详情编码")
    private String lastTicketInfoNo;
}
