package com.ycandyz.master.entities.mall;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description 商品推荐关联表 数据表字段映射类
 * @author WangWx
 * @since 2021-01-12
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@TableName("mall_item_recommend_relation")
@ApiModel(description="商品推荐关联表")
public class MallItemRecommendRelation extends Model {

   @TableId(value = "id", type = IdType.AUTO)
   private Long id;

   @ApiModelProperty(name = "item_recommend_setting_no", value = "推荐设置编号")
   private String itemRecommendSettingNo;

   @ApiModelProperty(name = "item_recommend_relation_no", value = "推荐设置关联商品编号")
   private String itemRecommendRelationNo;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "status", value = "状态：1: 正常，0: 无效")
   private Integer status;

   private Date createdTime;

   private Date updatedTime;


}
