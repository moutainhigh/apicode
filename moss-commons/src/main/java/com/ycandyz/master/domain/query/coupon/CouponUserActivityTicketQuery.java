package com.ycandyz.master.domain.query.coupon;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class CouponUserActivityTicketQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiParam(hidden = true)
    @ApiModelProperty(name = "shop_no",value = "门店编号")
    private String shopNo;

    @ApiModelProperty(name = "activity_no",value = "优惠宝编号")
    private String activityNo;

    @ApiModelProperty(value = "用户名/手机号")
    private String username;

    @ApiModelProperty(name = "status_type", value = "优惠券状态分类：0:待使用,1:过期,2:已使用")
    private Integer statusType;

    @ApiModelProperty(name = "created_time",value = "创建时间")
    @Condition(field = "create_time", condition = ConditionEnum.EQ)
    private Date createTime;

    @ApiModelProperty(name = "created_time_s",value = "创建时间起")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date createTimeS;

    @ApiModelProperty(name = "created_time_e",value = "创建时间止")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date createTimeE;


}
