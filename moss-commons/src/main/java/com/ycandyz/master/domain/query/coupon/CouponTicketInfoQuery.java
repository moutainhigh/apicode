package com.ycandyz.master.domain.query.coupon;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 优惠券详情表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="优惠券详情表-检索参数")
public class CouponTicketInfoQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠券详情编码")
    private String ticketInfoNo;

    @ApiModelProperty(value = "优惠券编码")
    private String ticketNo;

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
    private Long beginAt;

    @ApiModelProperty(value = "券生效结束时间")
    private Long endAt;

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

    @ApiModelProperty(value = "创建时间")
    private Long createdAt;

    @ApiModelProperty(value = "更新时间")
    private Long updateAt;

    //优惠券表中字段

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券id")
    private Long id;

    @ApiModelProperty(value = "券总数量")
    private Integer ticketSum;

    @ApiModelProperty(value = "优惠券最新修改详情编码")
    private String lastTicketInfoNo;

    //商品的编号列表
    @ApiModelProperty(value = "商品编号列表")
    private List<String> itemNoList;
}