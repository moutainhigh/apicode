package com.ycandyz.master.domain.response.coupon;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * @Description 发卷宝-优惠卷 Resp
 * </p>
 *
 * @author SanGang
 * @since 2020-11-17
 * @version 2.0
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CouponActivityTicketResp {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "优惠券编号")
    private String ticketNo;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠卷剩余数量")
    private Integer remainNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "券生效开始时间")
    private Date beginTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "券生效结束时间")
    private Date endTime;

    @ApiModelProperty(value = "优惠券状态：0:未开始，1:进行中，2:已结束，3:已停止")
    private Integer state;

    @ApiModelProperty(value = "说明备注")
    private String remark;

    @ApiModelProperty(value = "卷宝-优惠卷 ID")
    private Long activityTicketId;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;


}
