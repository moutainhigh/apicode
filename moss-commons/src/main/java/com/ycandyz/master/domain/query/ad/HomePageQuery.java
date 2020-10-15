package com.ycandyz.master.domain.query.ad;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * @Description 首页-页面表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="首页-页面表-检索参数")
public class HomePageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    @Condition(condition = ConditionEnum.EQ)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @Condition(condition = ConditionEnum.LIKE)
    private String name;

    @ApiModelProperty(value = "处于第几级")
    @Condition(condition = ConditionEnum.EQ)
    private Integer layer;

    @ApiModelProperty(value = "状态：1: 正常，0: 无效  默认1")
    @Condition(condition = ConditionEnum.EQ)
    private Boolean status;


}
