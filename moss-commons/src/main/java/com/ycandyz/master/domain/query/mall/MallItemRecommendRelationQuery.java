package com.ycandyz.master.domain.query.mall;

import com.ycandyz.master.annotation.Condition;
import com.ycandyz.master.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 商品推荐关联表 检索参数类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品推荐关联表-检索参数")
public class MallItemRecommendRelationQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemRecommendSettingNo;

    @ApiModelProperty(name = "item_recommend_relation_no", value = "推荐设置关联商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemRecommendRelationNo;

    @ApiModelProperty(name = "item_no", value = "商品编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemNo;

    @ApiModelProperty(name = "status", value = "状态：1: 正常，0: 无效")
    @Condition(condition = ConditionEnum.EQ)
    private Boolean status;

    @ApiModelProperty(name = "created_time", value = "起")
    @Condition(field = "created_time", condition = ConditionEnum.GE)
    private Date createdTimeS;
    
    @ApiModelProperty(name = "created_time", value = "止")
    @Condition(field = "created_time", condition = ConditionEnum.LE)
    private Date createdTimeE;

    @ApiModelProperty(name = "updated_time", value = "起")
    @Condition(field = "updated_time", condition = ConditionEnum.GE)
    private Date updatedTimeS;
    
    @ApiModelProperty(name = "updated_time", value = "止")
    @Condition(field = "updated_time", condition = ConditionEnum.LE)
    private Date updatedTimeE;



}
