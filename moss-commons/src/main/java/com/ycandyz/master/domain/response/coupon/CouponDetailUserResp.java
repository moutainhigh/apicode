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
public class CouponDetailUserResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(name = "user_id",value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(name = "use_type",value = "使用门槛：0:无门槛，1:订单满减可用")
    private Integer useType;

    @ApiModelProperty(name = "full_money",value = "订单满多少元可用")
    private BigDecimal fullMoney;

    @ApiModelProperty(name = "discount_money",value = "优惠金额")
    private BigDecimal discountMoney;

    @ApiModelProperty(name = "status", value = "优惠券状态分类：0:待使用,1:过期,2:已使用")
    private Integer status;

    @ApiModelProperty(name = "statusName", value = "优惠券状态中文")
    private String statusName;

    @ApiModelProperty(value = "操作类型(1启用,0停止)",required=true)
    private Integer enabled;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "create_time",value = "参与时间")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(name = "end_time",value = "优惠卷截止时间")
    private Date endTime;


}
