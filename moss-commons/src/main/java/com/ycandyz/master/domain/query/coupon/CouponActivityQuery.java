package com.ycandyz.master.domain.query.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.ycandyz.master.underline.Hump;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 发卷宝 检索参数类
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
@ApiModel(description="发卷宝-检索参数")
public class CouponActivityQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiParam(hidden = true)
    @ApiModelProperty(name = "shop_no",value = "门店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(name = "name",value = "活动名称")
    @Condition(condition = ConditionEnum.EQ)
    private String name;

    @ApiModelProperty(name = "create_time",value = "创建时间")
    @Condition(field = "create_time", condition = ConditionEnum.EQ)
    private Date createTime;

    @ApiModelProperty(name = "create_time_s",value = "创建时间起")
    @Condition(field = "create_time", condition = ConditionEnum.GE)
    private Date createTimeS;

    @ApiModelProperty(name = "create_time_e",value = "创建时间止")
    @Condition(field = "create_time", condition = ConditionEnum.LE)
    private Date createTimeE;




}
