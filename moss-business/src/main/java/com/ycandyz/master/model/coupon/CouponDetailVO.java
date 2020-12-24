package com.ycandyz.master.model.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.domain.response.mall.MallItemResp;
import com.ycandyz.master.entities.coupon.CouponDetail;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * @Description 优惠券详情表 VO
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@TableName("coupon_detail")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponDetailVO extends CouponDetail {

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
    private Integer validityType;

    @ApiModelProperty(value = "券生效开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(value = "券生效结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    private String obtain;

    @ApiModelProperty(value = "说明备注")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "标记优惠券关联详情：0-以前；1-当前")
    private Integer status;

    //冗余字段

    @ApiModelProperty(value = "剩余数量")
    private Integer surplusNum;

    @ApiModelProperty(value = "生成时间")
    private Long ticketCreatedAt;

    @ApiModelProperty(value = "门店编号")
    private String shopNo;

    @ApiModelProperty(value = "券总数量")
    private Integer couponSum;

    @ApiModelProperty(value = "领取数量")
    private Integer obtainNum;

    @ApiModelProperty(value = "核销数量")
    private Integer useNum;

    @ApiModelProperty(value = "优惠券状态：0:停止,1:开始")
    private Integer couponStatus;

    @ApiModelProperty(value = "关联商品信息")
    private List<MallItemResp> mallItemResps;

    @ApiModelProperty(value = "成交金额   订单金额（含券的金额，不含运费的金额）")
    private BigDecimal dealAmount;

    //商品的编号列表
    @ApiModelProperty(name = "item_no_list",value = "商品编号列表")
    private List<String> itemNoList;

    @ApiModelProperty(name = "obtain_list",value = "领取页面String转数组")
    private List<String> obtainList;
}
