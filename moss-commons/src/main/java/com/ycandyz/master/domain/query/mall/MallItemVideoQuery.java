package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 商品视频信息 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-11-10
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品视频信息-检索参数")
public class MallItemVideoQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(value = "商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "视频类型")
    @Condition(condition = ConditionEnum.EQ)
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "视频标题")
    @Condition(condition = ConditionEnum.LIKE)
    private String title;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "审核状态(0待审核,1通过,2未通过)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer audit;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "投诉状态(0正常,1被投诉)")
    @Condition(condition = ConditionEnum.EQ)
    private Integer status;

}
