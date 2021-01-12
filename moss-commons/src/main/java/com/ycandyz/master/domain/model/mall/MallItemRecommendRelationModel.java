package com.ycandyz.master.domain.model.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 商品推荐关联表 Model
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品推荐关联表-Model")
public class MallItemRecommendRelationModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
   private String itemRecommendSettingNo;

   @ApiModelProperty(name = "item_recommend_relation_no", value = "推荐设置关联商品编号")
   private String itemRecommendRelationNo;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "status", value = "状态：1: 正常，0: 无效")
   private Boolean status;

   private Date createdTime;

   private Date updatedTime;


}

