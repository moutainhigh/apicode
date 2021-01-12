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
 * @Description 商品推荐设置表 检索参数类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description="商品推荐设置表-检索参数")
public class MallItemRecommendSettingQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Condition(field = "id", condition = ConditionEnum.EQ)
    private Long id;

    @ApiModelProperty(name = "shop_no", value = "商店编号")
    @Condition(condition = ConditionEnum.EQ)
    private String shopNo;

    @ApiModelProperty(name = "item_recommend_no", value = "推荐编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemRecommendNo;

    @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
    @Condition(condition = ConditionEnum.EQ)
    private String itemRecommendSettingNo;

    @ApiModelProperty(name = "show_name", value = "展示名称")
    @Condition(condition = ConditionEnum.EQ)
    private String showName;

    @ApiModelProperty(name = "is_recommend", value = "是否推荐 0不推荐 1推荐")
    @Condition(condition = ConditionEnum.EQ)
    private Integer isRecommend;

    @ApiModelProperty(name = "recommend_way", value = "推荐方式 1自动推荐 2手动选择")
    @Condition(condition = ConditionEnum.EQ)
    private Integer recommendWay;

    @ApiModelProperty(name = "recommend_type", value = "自动推荐类型 1 最新添加的商品 2 全部商品浏览TOP 3 全部商品销售TOP 4 全部商品排序值TOP")
    @Condition(condition = ConditionEnum.EQ)
    private Integer recommendType;

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
