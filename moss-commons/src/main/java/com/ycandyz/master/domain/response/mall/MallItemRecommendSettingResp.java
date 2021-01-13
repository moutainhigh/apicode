package com.ycandyz.master.domain.response.mall;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 商品推荐设置表 Resp
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品推荐设置表-Resp")
public class MallItemRecommendSettingResp implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   @ApiModelProperty(name = "shop_no", value = "商店编号")
   private String shopNo;

   @ApiModelProperty(name = "item_recommend_no", value = "推荐编号")
   private String itemRecommendNo;

   @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
   private String itemRecommendSettingNo;

   @ApiModelProperty(name = "show_name", value = "展示名称")
   private String showName;

   @ApiModelProperty(name = "is_recommend", value = "是否推荐 0不推荐 1推荐")
   private Integer isRecommend;

   @ApiModelProperty(name = "recommend_way", value = "推荐方式 1自动推荐 2手动选择")
   private Integer recommendWay;

   @ApiModelProperty(name = "recommend_type", value = "自动推荐类型 1 最新添加的商品 2 全部商品浏览TOP 3 全部商品销售TOP 4 全部商品排序值TOP")
   private Integer recommendType;

   private Date createdTime;

   private Date updatedTime;


}

