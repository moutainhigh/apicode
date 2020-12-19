package com.ycandyz.master.domain.query.coupon;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="优惠卷-使用统计传参")
public class CouponUseUserQuery implements Serializable {

    @ApiModelProperty(name = "user_msg",value = "用户")
    private Integer userMsg;

    @ApiModelProperty(name = "coupon_status",value = "优惠券状态 0未使用,1已使用")
    private Integer couponStatus;

    @ApiModelProperty(name = "source",value = "领取来源 0推广,1活动,2页面")
    private Integer source;

    @ApiModelProperty(name = "begin_take_time",value = "领取开始时间")
    private Data beginTakeTime;

    @ApiModelProperty(name = "end_take_time",value = "领取结束时间")
    private Data endTakeTime;

    @ApiModelProperty(name = "begin_order_time",value = "领取开始时间")
    private Data beginOrderTime;

    @ApiModelProperty(name = "end_order_time",value = "领取结束时间")
    private Data endOrderTime;

    @ApiModelProperty(name = "source",value = "状态：0-全部，1-已使用，2-使用中，3-过期")
    private Integer status;
}
