package com.ycandyz.master.domain.query.coupon;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 优惠卷 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="优惠卷-检索参数")
public class CouponTicketQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门店编号")
    private String shopNo;

    @ApiModelProperty(value = "优惠券编码")
    private String ticketNo;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "创建开始时间")
    private Long createdBeginAt;

    @ApiModelProperty(value = "创建结束时间")
    private Long createdEndAt;

    @ApiModelProperty(value = "优惠券状态：0:未开始，1:进行中，2:已结束，3:已停止")
    private Integer state;

}
