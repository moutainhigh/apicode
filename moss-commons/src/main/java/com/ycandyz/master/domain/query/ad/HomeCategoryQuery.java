package com.ycandyz.master.domain.query.ad;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * @Description 首页- 轮播图分类表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-15
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="首页- 轮播图分类表-检索参数")
public class HomeCategoryQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(value = "分类编号")
    @Condition(condition = ConditionEnum.EQ)
    private String categoryNo;


}
