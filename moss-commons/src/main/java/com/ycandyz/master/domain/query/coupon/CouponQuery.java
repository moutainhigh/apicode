package com.ycandyz.master.domain.query.coupon;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="优惠卷-检索参数")
public class CouponQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiParam(hidden = true)
    @ApiModelProperty(name = "shop_no",value = "门店编号")
    private String shopNo;

    @ApiModelProperty(name = "name",value = "优惠券名称")
    private String name;

    @ApiModelProperty(name = "create_time_begin",value = "创建时间起")
    private Date createTimeBegin;

    @ApiModelProperty(name = "create_time_end",value = "创建时间止")
    private Date createTimeEnd;

    @ApiModelProperty(name = "sub_status",value = "状态：全部状态不传；0-未未始，1-进行中，2-已结束")
    private Integer subStatus;

}
