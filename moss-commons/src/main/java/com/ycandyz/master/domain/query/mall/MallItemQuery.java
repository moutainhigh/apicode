package com.ycandyz.master.domain.query.mall;

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
 * @Description 商品表 检索参数类
 * </p>
 *
 * @author SanGang
 * @since 2020-10-13
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品表-检索参数")
public class MallItemQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(value = "分类编号")
    @Condition(condition = ConditionEnum.EQ)
    private String categoryNo;

    @ApiModelProperty(value = "商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemNo;

    @ApiModelProperty(value = "商品名称")
    @Condition(condition = ConditionEnum.LIKE)
    private String itemName;

    @ApiModelProperty(value = "优惠券id")
    @Condition(condition = ConditionEnum.LIKE)
    private Long couponId;

}
