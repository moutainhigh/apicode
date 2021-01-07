package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * @Description 商品分销 Model
 * </p>
 *
 * @author SanGang
 * @since 2020-12-19
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品分销-Model")
public class MallItemShareModel {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "is_enable", value = "1:启用,0禁用")
   private Integer isEnable;

   @ApiModelProperty(name = "share_level_type", value = "分销层级 0未开启 1 一层 2 三层 3二层")
   private Integer shareLevelType;

   @ApiModelProperty(name = "is_updated_share", value = "是否设置过分销")
   private Boolean isUpdatedShare;

   @ApiModelProperty(name = "is_enable_share", value = "是否开启分销 0:不开启 1:开启")
   private Integer isEnableShare;

   @ApiModelProperty(name = "share_method", value = "U团长分销佣金的方式0:按比例 1:按金额")
   private Integer shareMethod;

   @ApiModelProperty(name = "share_rate", value = "U团长分销佣金按比例")
   private BigDecimal shareRate;

   @ApiModelProperty(name = "share_amount", value = "U团长分销佣金按金额")
   private BigDecimal shareAmount;

   @ApiModelProperty(name = "share_level_method", value = "U团长管理佣金的方式0:按比例 1:按金额")
   private Integer shareLevelMethod;

   @ApiModelProperty(name = "share_level_rate", value = "U团长管理佣金按比例")
   private BigDecimal shareLevelRate;

   @ApiModelProperty(name = "share_level_amount", value = "U团长管理佣金按金额")
   private BigDecimal shareLevelAmount;

   @ApiModelProperty(name = "share_second_method", value = "U达人参与分佣的方式0:按比例 1:按金额 ")
   private Integer shareSecondMethod;

   @ApiModelProperty(name = "share_second_level_method", value = "U达人参与管理佣金的方式0:按比例 1:按金额 ")
   private Integer shareSecondLevelMethod;

   @ApiModelProperty(name = "share_second_rate", value = "U达人分销佣金按比例")
   private BigDecimal shareSecondRate;

   @ApiModelProperty(name = "share_second_amount", value = "U达人分销佣金按金额")
   private BigDecimal shareSecondAmount;

   @ApiModelProperty(name = "share_second_level_rate", value = "U达人管理佣金按比例")
   private BigDecimal shareSecondLevelRate;

   @ApiModelProperty(name = "share_second_level_amount", value = "U达人管理佣金按金额")
   private BigDecimal shareSecondLevelAmount;

   @ApiModelProperty(name = "share_third_method", value = "U掌柜参与分佣的方式0:按比例 1:按金额 ")
   private Integer shareThirdMethod;

   @ApiModelProperty(name = "share_third_level_method", value = "U掌柜参与管理佣金的方式0:按比例 1:按金额 ")
   private Integer shareThirdLevelMethod;

   @ApiModelProperty(name = "share_third_rate", value = "U掌柜分销佣金按比例")
   private BigDecimal shareThirdRate;

   @ApiModelProperty(name = "share_third_amount", value = "U掌柜分销佣金按金额")
   private BigDecimal shareThirdAmount;

   @ApiModelProperty(name = "share_third_level_rate", value = "U掌柜管理佣金按比例")
   private BigDecimal shareThirdLevelRate;

   @ApiModelProperty(name = "share_third_level_amount", value = "U掌柜管理佣金按金额")
   private BigDecimal shareThirdLevelAmount;


}

