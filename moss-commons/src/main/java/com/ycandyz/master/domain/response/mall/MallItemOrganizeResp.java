package com.ycandyz.master.domain.response.mall;

import com.ycandyz.master.entities.mall.MallItemOrganize;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
 import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.Serializable;

/**
 * <p>
 * @Description 商品-集团 Resp
 * </p>
 *
 * @author SanGang
 * @since 2021-01-05
 * @version 2.0
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description="商品-集团-Resp")
public class MallItemOrganizeResp implements Serializable {

   private static final long serialVersionUID = 1L;

   @ApiModelProperty(name = "id", value = "ID")
   private Long id;

   @ApiModelProperty(name = "shop_no", value = "店铺编号")
   private String shopNo;

   @ApiModelProperty(name = "organize_item_no", value = "集团商品编号")
   private String organizeItemNo;

   @ApiModelProperty(name = "item_no", value = "商品编号")
   private String itemNo;

   @ApiModelProperty(name = "category_no", value = "集团商品分类编号")
   private String categoryNo;

   @ApiModelProperty(name = "create_time", value = "创建时间")
   private Date createTime;

   @ApiModelProperty(name = "update_time", value = "更新时间")
   private Date updateTime;


}

