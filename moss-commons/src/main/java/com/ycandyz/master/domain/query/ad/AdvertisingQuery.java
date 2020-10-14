package com.ycandyz.master.domain.query.ad;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 首页-广告位 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="首页-广告位-检索参数")
public class AdvertisingQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    @Condition(condition = ConditionEnum.LIKE)
    private String title;

    @ApiModelProperty(value = "发布日期起")
    @Condition(field = "release_time", condition = ConditionEnum.GE)
    private Date releaseTimeS;

    @ApiModelProperty(value = "发布日期")
    @Condition(field = "release_time", condition = ConditionEnum.EQ)
    private Date releaseTime;
    
    @ApiModelProperty(value = "发布日期止")
    @Condition(field = "release_time", condition = ConditionEnum.LE)
    private Date releaseTimeE;

    @ApiModelProperty(value = "类别(0:商品,1页面)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer type;

    @ApiModelProperty(value = "创建时间起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;

    @ApiModelProperty(value = "创建时间")
    @Condition(field = "created_time", condition = ConditionEnum.EQ)
    private Date createdTime;

    @ApiModelProperty(value = "创建时间止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

}
