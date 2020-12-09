package com.ycandyz.master.domain.response.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
public class CouponTicketResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "活动开始时间")
    private Date beginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "活动结束时间")
    private Date endTime;

    @ApiModelProperty(value = "优惠券状态：0:停止；1:启用")
    private Integer status;

    @ApiModelProperty(value = "说明备注")
    private String remark;

    @ApiModelProperty(value = "优惠卷剩余数量")
    private Integer remainNum;


}
